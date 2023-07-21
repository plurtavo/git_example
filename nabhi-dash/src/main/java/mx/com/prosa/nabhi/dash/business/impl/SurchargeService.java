package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.ISurchargeService;
import mx.com.prosa.nabhi.dash.core.IDataBaseService;
import mx.com.prosa.nabhi.dash.core.IInstitutionService;
import mx.com.prosa.nabhi.misc.domain.complete.entity.SurchargeEntity;
import mx.com.prosa.nabhi.misc.domain.complete.entity.composite.SurchargeIdentity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.SurchargeRepository;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.Surcharge;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class SurchargeService implements ISurchargeService {

    private final SurchargeRepository surchargeRepository;
    private final ModelMapper mapper;
    private final IDataBaseService dataBaseService;
    private final IInstitutionService institutionService;
    private static final Logger LOGGER = LoggerFactory.getLogger( SurchargeService.class );
    private static final String FIID_PROSA = "PROS";
    private static final String TABLE = "SURCHARGE";
    private static final String ERROR = "para el modulo " + TABLE;
    private static final Class < SurchargeEntity > domainClass = SurchargeEntity.class;
    private static final Class < Surcharge > dtoClass = Surcharge.class;
    private final Type listType = new TypeToken < List < Surcharge > >() {
    }.getType();

    @Autowired
    public SurchargeService( SurchargeRepository surchargeRepository, ModelMapper mapper, IDataBaseService dataBaseService, IInstitutionService institutionService ) {
        this.surchargeRepository = surchargeRepository;
        this.mapper = mapper;
        this.dataBaseService = dataBaseService;
        this.institutionService = institutionService;
    }

    @Override
    public String create( Surcharge value ) throws DataBaseException {
        try {
            SurchargeEntity entity = mapper.map( value, domainClass );
            dataBaseService.create( surchargeRepository, entity.getSurchargeId(), entity );
            institutionService.update( entity.getSurchargeId().getFiidAcquirer(), entity );
            return "Surcharge Creado correctamente";
        } catch ( DataBaseException | IDFException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public String modify( Surcharge value ) throws DataBaseException {
        SurchargeEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.modify( surchargeRepository, entity.getSurchargeId(), entity );
            return "Surcharge Modificado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public String delete( Surcharge value ) throws DataBaseException, IDFException {
        SurchargeEntity entity = mapper.map( value, domainClass );
        try {
            institutionService.delete( entity.getSurchargeId().getFiidAcquirer(), entity );
            dataBaseService.delete( surchargeRepository, entity.getSurchargeId() );
            return "Surcharge Eliminado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public Surcharge findById( String fiidA, String fiidB, String tranCode ) throws DataBaseException {
        SurchargeIdentity surchargeIdentity = new SurchargeIdentity( fiidA, fiidB, tranCode );
        try {
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( "Buscando Surcharge con llave %s %s %s", fiidA, fiidB, tranCode ) );
            }
            SurchargeEntity entity = dataBaseService.findById( surchargeRepository, surchargeIdentity );
            return mapper.map( entity, dtoClass );
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public List < Surcharge > findAll() throws DataBaseException, IDFException {
        try {
            List < SurchargeEntity > entities;
            IDFEntityKey key = institutionService.lookupUser();
            if ( key.getFiid().equals( FIID_PROSA ) ) {
                entities = dataBaseService.findByAll( surchargeRepository );
            } else {
                entities = institutionService.findSurcharges( key.getFiid() );
            }
            return mapper.map( entities, listType );
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }

    }

    @Override
    public List < String > findAllOnlyNames() throws DataBaseException, IDFException {
        List < Surcharge > surcharges = findAll();
        List < String > names = new ArrayList <>();
        for ( Surcharge surcharge : surcharges ) {
            names.add( surcharge.getSurchargeId().toString() );
        }
        return names;
    }
}
