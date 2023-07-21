package mx.com.prosa.nabhi.dash.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import mx.com.prosa.nabhi.dash.batch.model.*;
import mx.com.prosa.nabhi.misc.domain.algorithm.dto.DispensedAlgorithm;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.acv.FileException;
import mx.com.prosa.nabhi.misc.model.devices.DevicesWrapper;
import mx.com.prosa.nabhi.misc.model.jdb.Package;
import mx.com.prosa.nabhi.misc.model.jdb.Prefix;
import mx.com.prosa.nabhi.misc.model.jdb.*;
import mx.com.prosa.nabhi.misc.model.jdb.composite.SurchargeId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class YAMLLoader {

    private static final String MESSAGE = "Se recuperan %d entidades";

    private static final Logger LOGGER = LoggerFactory.getLogger( YAMLLoader.class );
    private final SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" );


    private final ObjectMapper mapper = new ObjectMapper( new YAMLFactory() );
    private final ModelMapper modelMapper = new ModelMapper();

    public Schema getSchema( String data ) throws FileException {
        try {
            return mapper.readValue( data, Schema.class );
        } catch ( IllegalArgumentException | IOException e ) {
            throw new FileException( CatalogError.FILE_NO_READ, e.getMessage() );
        }
    }

    public List < IDF > getInstitutions( String data ) throws FileException {
        try {
            Institutions institutions = mapper.readValue( data, Institutions.class );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( MESSAGE, institutions.getInstitutionsList().size() ) );
            }
            return convert( institutions );
        } catch ( IllegalArgumentException | IOException e ) {
            throw new FileException( CatalogError.FILE_NO_READ, e.getMessage() );
        }
    }

    public List < ATD > getATMS( String data ) throws FileException {
        try {
            ATMS atms = mapper.readValue( data, ATMS.class );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( MESSAGE, atms.getAtmList().size() ) );
            }
            return convert( atms );
        } catch ( IllegalArgumentException | IOException e ) {
            throw new FileException( CatalogError.FILE_NO_READ, e.getMessage() );
        }
    }

    public List < APC > getAcquirerProfiles( String data ) throws FileException {
        try {
            AcquirerProfilesMain acquirerProfilesMain = mapper.readValue( data, AcquirerProfilesMain.class );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( MESSAGE, acquirerProfilesMain.getAcquirerProfiles().size() ) );
            }
            return convert( acquirerProfilesMain );
        } catch ( IllegalArgumentException | IOException e ) {
            throw new FileException( CatalogError.FILE_NO_READ, e.getMessage() );
        }
    }

    public List < Surcharge > geSurcharges( String data ) throws FileException {
        try {
            SurchargeFees surchargeFees = mapper.readValue( data, SurchargeFees.class );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( MESSAGE, surchargeFees.getSurcharges().size() ) );
            }
            return convert( surchargeFees );
        } catch ( IllegalArgumentException | IOException e ) {
            throw new FileException( CatalogError.FILE_NO_READ, e.getMessage() );
        }
    }

    public List < Prefix > getPrefixes( String data ) throws FileException {
        try {
            Prefixes prefixes = mapper.readValue( data, Prefixes.class );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( MESSAGE, prefixes.getBins().size() ) );
            }
            return convert( prefixes );
        } catch ( IllegalArgumentException | IOException e ) {
            throw new FileException( CatalogError.FILE_NO_READ, e.getMessage() );
        }
    }

    public DevicesWrapper getDevices() throws FileException {
        try {
            return mapper.readValue( this.getClass().getResourceAsStream( "/devices.yml" ), DevicesWrapper.class );
        } catch ( IllegalArgumentException | IOException e ) {
            throw new FileException( CatalogError.FILE_NO_READ, e.getMessage() );
        }
    }

    private List < IDF > convert( Institutions institutions ) {
        Date now = new Date( System.currentTimeMillis() );
        Date tomorrow = new Date( ( System.currentTimeMillis() + ( 86400 * 1000 ) ) );
        List < IDF > entities = new ArrayList <>();
        for ( Institution institution : institutions.getInstitutionsList() ) {
            IDF entity = modelMapper.map( institution, IDF.class );
            entity.setCurrentBusinessDay( formatter.format( now ) );
            entity.setNextBusinessDay( formatter.format( tomorrow ) );
            entity.setForcedCutOver( "22:00" );
            Country country = new Country();
            entity.setCountry( country );
            country.setCountryCode( "484" );
            if ( institution.getCountyCode() != null && !institution.getCountyCode().isEmpty() ) {
                country.setCountryCode( institution.getCountyCode() );
            }
            entity.setLogicalNet( "PRO1" );
            if ( institution.getLogicalNet() != null && !institution.getLogicalNet().isEmpty() ) {
                entity.setLogicalNet( institution.getLogicalNet() );
            }
            entity.setAgreement( "" );
            entities.add( entity );
        }
        return entities;
    }

    private List < ATD > convert( ATMS atms ) throws FileException {
        List < ATD > entities = new ArrayList <>();
        for ( ATM atm : atms.getAtmList() ) {
            ATD entity = modelMapper.map( atm, ATD.class );
            if ( atm.getDeviceType().equals( "NCR" ) ) {
                entity.setDeviceType( "22" );
            } else if ( atm.getDeviceType().equals( "DIEBOLD" ) ) {
                entity.setDeviceType( "30" );
            } else {
                entity.setDeviceType( "B3" );
            }
            IDF idf = new IDF( atm.getFiid() );
            entity.setIdf( idf );
            County county = new County();
            county.setCountyCodeId( atm.getCountyCodeId() );
            entity.setCounty( county );
            ScreenGroup screenGroup = new ScreenGroup();
            screenGroup.setGroupId( atm.getGroupId() );
            entity.setScreenGroup( screenGroup );
            NodeTCP iso = new NodeTCP();
            iso.setNodeName( atm.getNodeHiso() );
            entity.setNodeHiso( iso );
            NodeTCP jke = new NodeTCP();
            jke.setNodeName( atm.getNodeMtvk() );
            entity.setNodeMtvk( jke );
            entity.setTerminalDevices( getDevices() );
            Package pack = new Package();
            pack.setName( atm.getPackageName() );
            entity.setPackageName( pack );
            DispensedAlgorithm dispensedAlgorithm = new DispensedAlgorithm();
            dispensedAlgorithm.setId( atm.getAlgorithmId() );
            entity.setDispensedAlgorithm( dispensedAlgorithm );
            entities.add( entity );
        }
        return entities;
    }

    private List < APC > convert( AcquirerProfilesMain acquirerProfilesMain ) {
        List < APC > entities = new ArrayList <>();
        for ( AcquirerProfile acquirerProfile : acquirerProfilesMain.getAcquirerProfiles() ) {
            APC entity = modelMapper.map( acquirerProfile, APC.class );
            entities.add( entity );
        }
        return entities;
    }

    private List < Surcharge > convert( SurchargeFees surchargeFees ) {
        List < Surcharge > entities = new ArrayList <>();
        for ( SurchargeFee surchargeFee : surchargeFees.getSurcharges() ) {
            Surcharge entity = modelMapper.map( surchargeFee, Surcharge.class );
            SurchargeId id = new SurchargeId();
            id.setFiidAcquirer( surchargeFee.getFiidAcquirer() );
            id.setFiidIssuing( surchargeFee.getFiidIssuing() );
            id.setTranCode( surchargeFee.getTranCode() );
            entity.setSurchargeId( id );
            entities.add( entity );
        }
        return entities;
    }

    private List < Prefix > convert( Prefixes prefixes ) {
        List < Prefix > entities = new ArrayList <>();
        for ( mx.com.prosa.nabhi.dash.batch.model.Prefix prefix : prefixes.getBins() ) {
            Prefix entity = modelMapper.map( prefix, Prefix.class );
            entities.add( entity );
        }
        return entities;
    }
}
