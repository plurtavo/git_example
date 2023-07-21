package mx.com.prosa.nabhi.jse.core.database.memory.atm;

import mx.com.prosa.nabhi.misc.domain.algorithm.entity.ATDForAlgorithmEntity;
import mx.com.prosa.nabhi.misc.domain.algorithm.entity.ATDForDevicesEntity;
import mx.com.prosa.nabhi.misc.domain.algorithm.repository.ATDForAlgorithmRepository;
import mx.com.prosa.nabhi.misc.domain.algorithm.repository.ATDForDeviceRepository;
import mx.com.prosa.nabhi.misc.domain.complete.entity.ATDEntity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.ATDRepository;
import mx.com.prosa.nabhi.misc.domain.personalized.entity.ATDBasicEntity;
import mx.com.prosa.nabhi.misc.domain.personalized.entity.ATDScreenEntity;
import mx.com.prosa.nabhi.misc.domain.personalized.entity.ATDTransactionalEntity;
import mx.com.prosa.nabhi.misc.domain.personalized.repository.ATDBasicRepository;
import mx.com.prosa.nabhi.misc.domain.personalized.repository.ATDScreenRepository;
import mx.com.prosa.nabhi.misc.domain.personalized.repository.ATDTransactionalRepository;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.model.jdb.ATD;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDBasic;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDDevice;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDScreen;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDTransactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ATMSearch {

    private final ATDRepository atdRepository;
    private final ATDTransactionalRepository atdTransactionalRepository;
    private final ATDForAlgorithmRepository atdForAlgorithmRepository;
    private final ATDScreenRepository atdScreenRepository;
    private final ATDBasicRepository atdBasicRepository;
    private final ModelMapper mapper;
    private final ATDForDeviceRepository deviceRepository;


    @Autowired
    public ATMSearch( ATDRepository atdRepository, ATDTransactionalRepository atdTransactionalRepository, ATDForAlgorithmRepository atdForAlgorithmRepository, ATDScreenRepository atdScreenRepository, ATDBasicRepository atdBasicRepository, ModelMapper mapper, ATDForDeviceRepository deviceRepository ) {
        this.atdRepository = atdRepository;
        this.atdTransactionalRepository = atdTransactionalRepository;
        this.atdForAlgorithmRepository = atdForAlgorithmRepository;
        this.atdScreenRepository = atdScreenRepository;
        this.atdBasicRepository = atdBasicRepository;
        this.mapper = mapper;
        this.deviceRepository = deviceRepository;
    }


    public ATDBasic searchBasic( String terminalId ) throws ATMException {
        Optional < ATDBasicEntity > oa = atdBasicRepository.findById( terminalId );
        if ( oa.isPresent() ) {
            return mapper.map( oa.get(), ATDBasic.class );
        } else {
            throw new ATMException( CatalogError.ATM_NOT_EXIST );
        }
    }

    private void saveBasic( ATDBasic basic ) {
        ATDBasicEntity entity = mapper.map( basic, ATDBasicEntity.class );
        atdBasicRepository.save( entity );
    }

    public void tranInProgress( String terminal, boolean flag ) throws ATMException {
        ATDBasic basic = searchBasic( terminal );
        basic.setActiveTrx( flag );
        saveBasic( basic );
    }

    public ATDDevice searchDevice( String terminalId ) throws ATMException {
        Optional < ATDForDevicesEntity > oa = deviceRepository.findById( terminalId );
        if ( oa.isPresent() ) {
            return mapper.map( oa.get(), ATDDevice.class );
        } else {
            throw new ATMException( CatalogError.ATM_NOT_EXIST );
        }
    }

    public ATDDevice searchDeviceAlgorithm( String terminalId ) throws ATMException {
        Optional < ATDForAlgorithmEntity > oa = atdForAlgorithmRepository.findById( terminalId );
        if ( oa.isPresent() ) {
            return mapper.map( oa.get(), ATDDevice.class );
        } else {
            throw new ATMException( CatalogError.ATM_NOT_EXIST );
        }
    }

    public void saveDevice( ATDDevice dev ) {
        ATDForDevicesEntity entity = mapper.map( dev, ATDForDevicesEntity.class );
        deviceRepository.save( entity );
    }

    public ATDScreen searchScreen( String terminalId ) throws ATMException {
        Optional < ATDScreenEntity > oa = atdScreenRepository.findById( terminalId );
        if ( oa.isPresent() ) {
            return mapper.map( oa.get(), ATDScreen.class );
        } else {
            throw new ATMException( CatalogError.ATM_NOT_EXIST );
        }
    }

    public ATDTransactional searchTransactional( String terminalId ) throws ATMException {
        Optional < ATDTransactionalEntity > oa = atdTransactionalRepository.findById( terminalId );
        if ( oa.isPresent() ) {
            return mapper.map( oa.get(), ATDTransactional.class );
        } else {
            throw new ATMException( CatalogError.ATM_NOT_EXIST );
        }
    }

    public void saveTransactional( ATDTransactional transactional ) {
        ATDTransactionalEntity entity = mapper.map( transactional, ATDTransactionalEntity.class );
        atdTransactionalRepository.save( entity );
    }

    public ATD search( String terminalId ) throws ATMException {
        Optional < ATDEntity > oa = atdRepository.findById( terminalId );
        if ( oa.isPresent() ) {
            return mapper.map( oa.get(), ATD.class );
        } else {
            throw new ATMException( CatalogError.ATM_NOT_EXIST );
        }
    }

    public void save( ATD atd ) {
        ATDEntity entity = mapper.map( atd, ATDEntity.class );
        atdRepository.save( entity );
    }
}
