package mx.com.prosa.nabhi.dash.redcat.forcedcutover;

import mx.com.prosa.nabhi.dash.redcat.ftpconnection.FTPClientRedcat;
import mx.com.prosa.nabhi.dash.redcat.ftpconnection.FTPClientTemplate;
import mx.com.prosa.nabhi.dash.redcat.report.BookJsonRedcat;
import mx.com.prosa.nabhi.dash.redcat.report.DATReportBuilder;
import mx.com.prosa.nabhi.dash.redcat.report.ExcelRedcatReportBuilder;
import mx.com.prosa.nabhi.dash.redcat.report.SheetJsonRedcat;
import mx.com.prosa.nabhi.misc.domain.redcat.dto.ATD;
import mx.com.prosa.nabhi.misc.domain.redcat.dto.Redcat;
import mx.com.prosa.nabhi.misc.domain.redcat.entity.ATDForRedcatEntity;
import mx.com.prosa.nabhi.misc.domain.redcat.entity.IDFForRedcatEntity;
import mx.com.prosa.nabhi.misc.domain.redcat.entity.RedcatEntity;
import mx.com.prosa.nabhi.misc.domain.redcat.repository.ATDForRedcatRepository;
import mx.com.prosa.nabhi.misc.domain.redcat.repository.RedcatRepository;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.dash.RedcatException;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Cassette;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Logical;
import mx.com.prosa.nabhi.misc.model.devices.constants.cdm.cassette.CassetteType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class RedcatJob implements ForcedCutOverListener {

    private static final Logger LOG = LoggerFactory.getLogger( RedcatJob.class );
    private static final String MXN = "MXN";
    private static final String USD = "USD";
    private static final String FORCE = "09";
    private static final String NOK = "NOK";
    private static final String OK = "OK";

    private final ATDForRedcatRepository atdForRedcatRepository;
    private final RedcatRepository redcatRepository;
    private final ExcelRedcatReportBuilder redcatReportBuilder;
    private final DATReportBuilder datReportBuilder;
    private final FTPClientRedcat ftpClientRedcat;
    private final FTPClientTemplate ftpClientTemplate;
    private final ModelMapper modelMapper;

    @Autowired
    public RedcatJob( ATDForRedcatRepository deviceRepository, RedcatRepository redcatRepository,
                      ExcelRedcatReportBuilder redcatReportBuilder, DATReportBuilder datReportBuilder,
                      FTPClientRedcat ftpClientRedcat, FTPClientTemplate ftpClientTemplate, ModelMapper modelMapper ) {

        this.atdForRedcatRepository = deviceRepository;
        this.redcatRepository = redcatRepository;
        this.redcatReportBuilder = redcatReportBuilder;
        this.datReportBuilder = datReportBuilder;
        this.ftpClientRedcat = ftpClientRedcat;
        this.ftpClientTemplate = ftpClientTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public void notifyForcedCutOver( String fiid ) {
        try {
            forceCutByFiid( fiid, true );
        } catch ( RedcatException e ) {
            LOG.error( "Enviar alerta de que el corte se realizo pero no se pudo guardar en el FTP" );
        }
    }


    public String forceCutByFiid( String fiid, boolean force ) throws RedcatException {
        IDFForRedcatEntity entity = new IDFForRedcatEntity();
        entity.setFiid( fiid );
        List < ATDForRedcatEntity > atdDeviceEntities = atdForRedcatRepository.findAllByIdf( entity );
        if ( LOG.isErrorEnabled() ) {
            LOG.info( String.format( "Cantidad de cajeros para fiid:%s : %d", fiid, atdDeviceEntities.size() ) );
        }
        if ( !atdDeviceEntities.isEmpty() ) {
            for ( ATDForRedcatEntity atd : atdDeviceEntities ) {
                String response = forceCutByATM( atd.getTerminalId(), atd.getIdf().getFiid() );
                if ( response.equals( NOK ) ) {
                    LOG.error( "Enviar una alerta de que el cajero no pudo hacer su corte" );
                }
            }

            try {
                ByteArrayOutputStream outputStream = takeDataFromBD( fiid, force );
                saveRemoteRedcat( outputStream, fiid );
            } catch ( RedcatException e ) {
                LOG.error( e.getMessage() );
                throw new RedcatException( CatalogError.REDCAT_ERROR_FTP_CONNECTION_SERVER );
            }
            return OK;
        }
        LOG.error( CatalogError.REDCAT_ERROR_EMTPY_FIID.getMessage() );
        return NOK;
    }

    public String forceCutByATM( String termId, String fiid ) {
        Optional < ATDForRedcatEntity > optional = atdForRedcatRepository.findById( termId );
        if ( optional.isPresent() ) {
            ATD atdDevice = modelMapper.map( optional.get(), ATD.class );
            Type listType = new TypeToken < List < Cassette > >() {
            }.getType();
            List < Cassette > cassettes = modelMapper.map( atdDevice.getTerminalDevices().getDispenser().getCassettes(), listType );
            Redcat redcat = new Redcat();
            redcat.setTermId( termId );
            redcat.setFiid( fiid );
            redcat.setCoutType( FORCE );
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "Tomando los contadores para el reporte del cajero: %s", termId ) );
            }
            takeDayCounters( cassettes, redcat );
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "Guardando los datos del corte en base BD para cajero: %s", termId ) );
            }
            saveRedcatToBD( redcat );
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "Reiniciando las contadores diarios del cajero: %s", termId ) );
            }
            resetDayCounters( cassettes, atdDevice );
            return OK;
        }
        LOG.error( CatalogError.REDCAT_ERROR_ATM_NOT_EXISTS.getMessage() );
        return NOK;
    }

    private void takeDayCounters( List < Cassette > list, Redcat redcat ) {
        int totalOutMXN = 0;
        int totalOutUSD = 0;
        int remanenteMXN = 0;
        int remanenteUSD = 0;
        for ( Cassette cassette : list ) {
            switch ( cassette.getCassetteIndex() ) {
                case 1:
                    redcat.setCout1( cassette.getLogical().getTotalDispensed() * cassette.getDenomination() );
                    redcat.setEnd1( cassette.getPhysical().getCurrent() * cassette.getDenomination() );
                    redcat.setDenomination1( cassette.getDenomination() );
                    redcat.setCurrency1( cassette.getCurrency() );
                    break;
                case 2:
                    redcat.setCout2( cassette.getLogical().getTotalDispensed() * cassette.getDenomination() );
                    redcat.setEnd2( cassette.getPhysical().getCurrent() * cassette.getDenomination() );
                    redcat.setDenomination2( cassette.getDenomination() );
                    redcat.setCurrency2( cassette.getCurrency() );
                    break;
                case 3:
                    redcat.setCout3( cassette.getLogical().getTotalDispensed() * cassette.getDenomination() );
                    redcat.setEnd3( cassette.getPhysical().getCurrent() * cassette.getDenomination() );
                    redcat.setDenomination3( cassette.getDenomination() );
                    redcat.setCurrency3( cassette.getCurrency() );
                    break;
                case 4:
                    redcat.setCout4( cassette.getLogical().getTotalDispensed() * cassette.getDenomination() );
                    redcat.setEnd4( cassette.getPhysical().getCurrent() * cassette.getDenomination() );
                    redcat.setDenomination4( cassette.getDenomination() );
                    redcat.setCurrency4( cassette.getCurrency() );
                    break;
                case 5:
                    redcat.setCout5( cassette.getLogical().getTotalDispensed() * cassette.getDenomination() );
                    redcat.setEnd5( cassette.getPhysical().getCurrent() );
                    redcat.setDenomination5( cassette.getDenomination() );
                    redcat.setCurrency5( cassette.getCurrency() );
                    break;
                case 6:
                    redcat.setCout6( cassette.getLogical().getTotalDispensed() * cassette.getDenomination() );
                    redcat.setEnd6( cassette.getPhysical().getCurrent() * cassette.getDenomination() );
                    redcat.setDenomination6( cassette.getDenomination() );
                    redcat.setCurrency6( cassette.getCurrency() );
                    break;
                default:
                    LOG.error( CatalogError.REDCAT_ERROR_CASSETTES_INDEX.getMessage() );
            }
            if ( isDispenserCassette( cassette.getType() ) ) {
                if ( cassette.getCurrency().equals( MXN ) ) {
                    totalOutMXN += cassette.getLogical().getTotalDispensed() * cassette.getDenomination();
                    remanenteMXN += cassette.getPhysical().getCurrent() * cassette.getDenomination();
                } else if ( cassette.getCurrency().equals( USD ) ) {
                    totalOutUSD += cassette.getLogical().getTotalDispensed() * cassette.getDenomination();
                    remanenteUSD += cassette.getPhysical().getCurrent() * cassette.getDenomination();
                } else {
                    LOG.error( CatalogError.REDCAT_ERROR_CASSETTES_CURRENCY.getMessage() );
                }
            }
        }
        redcat.setTotalOutMXN( totalOutMXN );
        redcat.setTotalOutUSD( totalOutUSD );
        redcat.setRemanenteMXN( remanenteMXN );
        redcat.setRemanenteUSD( remanenteUSD );
    }

    private void resetDayCounters( List < Cassette > list, ATD atdDevice ) {
        List < Cassette > newLogicals = new ArrayList <>();
        for ( Cassette cassette : list ) {
            Logical logical = new Logical();
            logical.setInitialDayCount( cassette.getPhysical().getCurrent() );
            logical.setIncrement( 0 );
            logical.setEndDayCount( 0 );
            logical.setTotalDispensed( 0 );
            cassette.setLogical( logical );
            newLogicals.add( cassette );
        }
        atdDevice.getTerminalDevices().getDispenser().setCassettes( newLogicals );
        atdForRedcatRepository.save( modelMapper.map( atdDevice, ATDForRedcatEntity.class ) );
    }

    public ByteArrayOutputStream takeDataFromBD( String fiid, boolean force ) throws RedcatException {
        List < RedcatEntity > list = redcatRepository.findAllByFiid( fiid );
        List < Redcat > redcatList = new ArrayList <>();
        for ( RedcatEntity entity : list ) {
            redcatList.add( modelMapper.map( entity, Redcat.class ) );
        }
        if ( force ) {
            try {
                LOG.info( "Generando reporte de corte diario" );
                return datReportBuilder.build( redcatList );
            } catch ( IOException e ) {
                LOG.error( CatalogError.REDCAT_ERROR_DAT_REPORT.toString() );
                throw new RedcatException( CatalogError.REDCAT_ERROR_DAT_REPORT );
            }
        } else {
            LOG.info( "Generando reporte de excel para consulta" );
            return createBinaryExcel( redcatList, fiid );
        }
    }

    public ByteArrayOutputStream createBinaryExcel( List < Redcat > redcat, String fiid ) throws RedcatException {
        String[] columns = new String[ 38 ];
        columns[ 0 ] = "FIID";
        columns[ 1 ] = "CODIGO ATM";
        columns[ 2 ] = "COUT1";
        columns[ 3 ] = "COUT2";
        columns[ 4 ] = "COUT3";
        columns[ 5 ] = "COUT4";
        columns[ 6 ] = "COUT5";
        columns[ 7 ] = "COUT6";

        columns[ 8 ] = "TOTALOUT DOLARES";
        columns[ 9 ] = "TLF DOLARES";
        columns[ 10 ] = "DIFERENCIA DOLARES";

        columns[ 11 ] = "TOTALOUT MON LOCAL";
        columns[ 12 ] = "TLF MON LOCAL";
        columns[ 13 ] = "DIFEREN MON LOCAL";
        columns[ 14 ] = "END1";
        columns[ 15 ] = "END2";
        columns[ 16 ] = "END3";
        columns[ 17 ] = "END4";
        columns[ 18 ] = "END5";
        columns[ 19 ] = "END6";

        columns[ 20 ] = "REMANENTE DOLARES";
        columns[ 21 ] = "REMANENTE LOCAL";
        columns[ 22 ] = "DENOM_C1";
        columns[ 23 ] = "CDE_C1";
        columns[ 24 ] = "DENOM_C2";
        columns[ 25 ] = "CDE_C2";
        columns[ 26 ] = "DENOM_C3";
        columns[ 27 ] = "CDE_C3";
        columns[ 28 ] = "DENOM_C4";
        columns[ 29 ] = "CDE_C4";
        columns[ 30 ] = "DENOM_C5";
        columns[ 31 ] = "CDE_C5";
        columns[ 32 ] = "DENOM_C6";
        columns[ 33 ] = "CDE_C6";
        columns[ 34 ] = "FECHA INICIAL";
        columns[ 35 ] = "FECHA FINAL";
        columns[ 36 ] = "TIPO CORTE";
        SheetJsonRedcat sheet = new SheetJsonRedcat( fiid, columns, redcat );
        List < SheetJsonRedcat > listSheets = new ArrayList <>();
        listSheets.add( sheet );
        BookJsonRedcat book = new BookJsonRedcat( listSheets );
        try {
            return redcatReportBuilder.buildRedcatBook( book, fiid );
        } catch ( RedcatException e ) {
            LOG.error( "Error al crear el reporte de excel" );
            throw new RedcatException( CatalogError.REDCAT_ERROR_EXCEL_BUILD, e.getMessage() );
        }
    }

    private void saveRemoteRedcat( ByteArrayOutputStream stream, String fiid ) throws RedcatException {
        try {
            ftpClientRedcat.init( ftpClientTemplate.getUser(), ftpClientTemplate.getP(), ftpClientTemplate.getIp(), ftpClientTemplate.getPort() );
            ftpClientRedcat.sendFile( stream, fiid );
        } catch ( IOException e ) {
            LOG.error( CatalogError.REDCAT_ERROR_FTP_CONNECTION_SERVER.toString() );
            throw new RedcatException( CatalogError.REDCAT_ERROR_FTP_CONNECTION_SERVER );
        } finally {
            try {
                if ( LOG.isDebugEnabled() ) {
                    LOG.debug( "Desconectandose del servidor FTP" );
                }
                ftpClientRedcat.close();
            } catch ( IOException e ) {
                LOG.warn( String.format( "Error al desconectarse del servidor FTP %s", e.getMessage() ) );
            }
        }

    }

    private void saveRedcatToBD( Redcat redcat ) {
        SimpleDateFormat formatter = new SimpleDateFormat( "dd/MM/yy HH:mm" );
        Date endDate = new Date();
        formatter.format( endDate );
        redcat.setEndDate( endDate );
        Date startDate = lessDay( endDate );
        formatter.format( startDate );
        redcat.setStartDate( startDate );
        RedcatEntity entity = modelMapper.map( redcat, RedcatEntity.class );
        redcatRepository.save( entity );
    }

    private Date lessDay( Date date ) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        calendar.add( Calendar.DAY_OF_YEAR, -1 );
        return calendar.getTime();
    }

    private boolean isDispenserCassette( String type ) {
        return type.equals( CassetteType.BILLCASSETTE.name() ) || type.equals( CassetteType.RECYCLING.name() );
    }


}
