package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IMiscService;
import mx.com.prosa.nabhi.dash.core.IDataBaseService;
import mx.com.prosa.nabhi.misc.domain.complete.entity.*;
import mx.com.prosa.nabhi.misc.domain.complete.repository.*;
import mx.com.prosa.nabhi.misc.domain.personalized.entity.ATDDashEntity;
import mx.com.prosa.nabhi.misc.domain.personalized.repository.ATDDashRepository;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.model.jdb.Country;
import mx.com.prosa.nabhi.misc.model.jdb.County;
import mx.com.prosa.nabhi.misc.model.jdb.State;
import mx.com.prosa.nabhi.misc.model.jdb.TranAllowed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MiscService implements IMiscService {

    private final CountryRepository countryRepository;
    private final CountyRepository countyRepository;
    private final StateRepository stateRepository;
    private final PackageRepository packageRepository;
    private final TranAllowedRepository tranAllowedRepository;
    private final IDataBaseService dataBaseService;
    private final ModelMapper mapper;
    private ATDDashRepository atdDashRepository;

    @Autowired
    public MiscService( CountryRepository countryRepository, CountyRepository countyRepository, StateRepository stateRepository, PackageRepository packageRepository, TranAllowedRepository tranAllowedRepository, IDataBaseService dataBaseService, ModelMapper mapper ) {
        this.countryRepository = countryRepository;
        this.countyRepository = countyRepository;
        this.stateRepository = stateRepository;
        this.packageRepository = packageRepository;
        this.tranAllowedRepository = tranAllowedRepository;
        this.dataBaseService = dataBaseService;
        this.mapper = mapper;
    }

    @Autowired
    public void setAtdDashRepository( ATDDashRepository atdDashRepository ) {
        this.atdDashRepository = atdDashRepository;
    }

    @Override
    public Country findCountry( String countryCode ) throws DataBaseException {
        CountryEntity entity = dataBaseService.findById( countryRepository, countryCode );
        return mapper.map( entity, Country.class );
    }

    @Override
    public County findCounty( int countyCode ) throws DataBaseException {
        CountyEntity entity = dataBaseService.findById( countyRepository, countyCode );
        return mapper.map( entity, County.class );
    }

    @Override
    public State findState( String stateCode ) throws DataBaseException {
        StateEntity entity = dataBaseService.findById( stateRepository, stateCode );
        return mapper.map( entity, State.class );
    }

    @Override
    public TranAllowed findTranAllow( String code ) throws DataBaseException {
        TranAllowedEntity entity = dataBaseService.findById( tranAllowedRepository, code );
        return mapper.map( entity, TranAllowed.class );
    }

    @Override
    public List < String > findKeysState() throws DataBaseException {
        List< StateEntity > entities = stateRepository.findAll();
        List< String > keys = new ArrayList <>();
        if ( !entities.isEmpty() ){
            for ( StateEntity entity : entities ){
                keys.add( "[" + entity.getStateCode() + "] " + entity.getStateName() );
            }
            return keys;
        }else {
            throw new DataBaseException( CatalogError.DATABASE_FIND_LIST_EMPTY, "TBL_STATE" );
        }
    }

    @Override
    public List < String > findKeysCounty() throws DataBaseException {
        List< CountyEntity > entities = countyRepository.findAll();
        List< String > keys = new ArrayList <>();
        if ( !entities.isEmpty() ){
            for ( CountyEntity entity : entities ){
                keys.add( "[" + entity.getCountyCodeId() + "] " + entity.getCountyName() );
            }
            return keys;
        }else {
            throw new DataBaseException( CatalogError.DATABASE_FIND_LIST_EMPTY, "TBL_COUNTY" );
        }
    }

    @Override
    public List < String > findKeysPackage() throws DataBaseException {
        List< PackageEntity > entities = packageRepository.findAll();
        List< String > keys = new ArrayList <>();
        if ( !entities.isEmpty() ){
            for ( PackageEntity entity : entities ){
                keys.add( entity.getName() );
            }
            return keys;
        }else {
            throw new DataBaseException( CatalogError.DATABASE_FIND_LIST_EMPTY, "TBL_PACKAGE" );
        }
    }

    @Override
    public List < String > findKeysTranAllow() throws DataBaseException {
        List< TranAllowedEntity > entities = tranAllowedRepository.findAll();
        List< String > keys = new ArrayList <>();
        if ( !entities.isEmpty() ){
            for ( TranAllowedEntity entity : entities ){
                keys.add( entity.getAllowedCode() );
            }
            return keys;
        }else {
            throw new DataBaseException( CatalogError.DATABASE_FIND_LIST_EMPTY, "TBL_TRAN_ALLOW" );
        }
    }

    @Override
    public String licence( String fiid, String licence ) throws DataBaseException {
        IDFEntityKey idfEntityKey = new IDFEntityKey();
        idfEntityKey.setFiid( fiid );
        List < ATDDashEntity > entities = atdDashRepository.findAllByIdfEquals( idfEntityKey );
        updateLicence( entities, licence );
        return String.format( "Licencia %s actualizada a la red %s", licence, fiid );
    }

    @Override
    public String licence( String fiid, String licence, String atm ) throws DataBaseException {
        IDFEntityKey idfEntityKey = new IDFEntityKey();
        idfEntityKey.setFiid( fiid );
        Optional < ATDDashEntity > oa = atdDashRepository.findByTerminalIdAndIdfEquals( atm, idfEntityKey );
        if ( oa.isPresent() ) {
            ATDDashEntity entity = oa.get();
            entity.setSequence( licence );
            atdDashRepository.save( entity );
            return String.format( "Licencia %s actualizada a el cajero %s", licence, atm );
        } else {
           throw new DataBaseException( CatalogError.LICENSE_ERROR );
        }
    }

    private void updateLicence( List < ATDDashEntity > entities, String licence ) throws DataBaseException {
        if ( entities.isEmpty() ){
            throw new DataBaseException( CatalogError.DATABASE_FIND_LIST_EMPTY, "TBL_ATD, no existe cajeros por la fiid" );
        }
        for ( ATDDashEntity entity : entities ) {
            entity.setSequence( licence );
        }
        atdDashRepository.saveAll( entities );
    }

}
