package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IAPCService;
import mx.com.prosa.nabhi.dash.core.IDataBaseService;
import mx.com.prosa.nabhi.dash.core.IInstitutionService;
import mx.com.prosa.nabhi.misc.domain.complete.entity.APCEntity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.APCRepository;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.APC;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class APCService implements IAPCService {

    private final APCRepository apcRepository;
    private final ModelMapper mapper;
    private final IDataBaseService dataBaseService;
    private final IInstitutionService institutionService;
    private static final Logger LOGGER = LoggerFactory.getLogger( APCService.class );
    private static final String FIID_PROSA = "PROS";
    private static final String TABLE = "APC";
    private static final String ERROR = "para el modulo " + TABLE;
    private static final Class < APCEntity > domainClass = APCEntity.class;
    private static final Class < APC > dtoClass = APC.class;
    private final Type listType = new TypeToken < List < APC > >() {
    }.getType();

    @Autowired
    public APCService( APCRepository apcRepository, ModelMapper mapper, IDataBaseService dataBaseService, IInstitutionService institutionService ) {
        this.apcRepository = apcRepository;
        this.mapper = mapper;
        this.dataBaseService = dataBaseService;
        this.institutionService = institutionService;
    }

    @Override
    public String create( APC value ) throws DataBaseException {
        try {
            APCEntity entity = mapper.map( value, domainClass );
            apcRepository.save( entity );
            return "APC Creado correctamente";
        } catch ( EntityNotFoundException e ) {
            throw new DataBaseException( CatalogError.APC_NOT_REFERENCE );
        } catch ( DataIntegrityViolationException e ) {
            throw new DataBaseException( CatalogError.APC_DUPLICATE_NAME );
        } catch ( InvalidDataAccessApiUsageException e ) {
            throw new DataBaseException( CatalogError.APC_NOT_REFERENCE_2 );
        }
    }

    @Override
    public String modify( APC value ) throws DataBaseException {
        APCEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.modify( apcRepository, entity.getId(), entity );
            return "APC Modificado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public String delete( APC value ) throws DataBaseException {
        APCEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.delete( apcRepository, entity.getId() );
            return "APC Eliminado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public APC findById( String fiid, String from, String to, String tranCode ) throws DataBaseException {

        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( String.format( "Buscando APC con llave %s %s %s %s", fiid, from, to, tranCode ) );
        }
        Optional < APCEntity > optional = apcRepository.findByFiidAndTranCodeAndFormAcctAndToAcct( fiid, tranCode, from, to );
        if ( optional.isPresent() ) {
            return mapper.map( optional.get(), dtoClass );
        } else {
            throw new DataBaseException( CatalogError.APC_FIND_GROUP_EMPTY_RESULT_SET );
        }

    }

    @Override
    public List < APC > findAll() throws DataBaseException, IDFException {
        try {
            List < APCEntity > entities;
            IDFEntityKey key = institutionService.lookupUser();
            if ( key.getFiid().equals( FIID_PROSA ) ) {
                entities = dataBaseService.findByAll( apcRepository );
            } else {
                entities = apcRepository.findAllByFiid( key.getFiid() );
            }
            if ( !entities.isEmpty() ) {
                return mapper.map( entities, listType );
            } else {
                throw new DataBaseException( CatalogError.APC_FIND_GROUP_EMPTY_RESULT_SET );
            }
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }

    }

    @Override
    public List < String > findAllOnlyNames() throws DataBaseException, IDFException {
        List < APC > apcs = findAll();
        List < String > names = new ArrayList <>();
        for ( APC apc : apcs ) {
            names.add( apc.getId() + "-" + apc.getFiid() + "-" + apc.getTranCode() + "-" + apc.getFormAcct() + "-" + apc.getToAcct() );
        }
        return names;
    }
}
