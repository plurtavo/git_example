package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IIDFService;
import mx.com.prosa.nabhi.dash.core.IDataBaseService;
import mx.com.prosa.nabhi.dash.core.IInstitutionService;
import mx.com.prosa.nabhi.misc.domain.personalized.entity.IDFDashEntity;
import mx.com.prosa.nabhi.misc.domain.personalized.repository.IDFDashRepository;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.IDF;
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
public class IDFService implements IIDFService {

    private final IDFDashRepository idfDashRepository;
    private final ModelMapper mapper;
    private final IDataBaseService dataBaseService;
    private final IInstitutionService institutionService;
    private static final Logger LOGGER = LoggerFactory.getLogger( IDFService.class );
    private static final String FIID_PROSA = "PROS";
    private static final String TABLE = "IDF";
    private static final String ERROR = "para el modulo " + TABLE;
    private static final Class < IDFDashEntity > domainClass = IDFDashEntity.class;
    private static final Class < IDF > dtoClass = IDF.class;
    private final Type listType = new TypeToken < List < IDF > >() {
    }.getType();

    @Autowired
    public IDFService( IDFDashRepository idfDashRepository, ModelMapper mapper, IDataBaseService dataBaseService, IInstitutionService institutionService ) {
        this.idfDashRepository = idfDashRepository;
        this.mapper = mapper;
        this.dataBaseService = dataBaseService;
        this.institutionService = institutionService;
    }

    @Override
    public String create( IDF value ) throws DataBaseException {
        try {
            IDFDashEntity entity = mapper.map( value, domainClass );
            dataBaseService.create( idfDashRepository, entity.getFiid(), entity );
            return "IDF Creado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public String modify( IDF value ) throws DataBaseException {
        IDFDashEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.modify( idfDashRepository, entity.getFiid(), entity );
            return "IDF Modificado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public String delete( IDF value ) throws DataBaseException, IDFException {
        IDFDashEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.delete( idfDashRepository, entity.getFiid() );
            return "IDF Eliminado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public IDF findById( String fiid ) throws DataBaseException {
        try {
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( "Buscando IDF con llave %s ", fiid ) );
            }
            IDFDashEntity entity = dataBaseService.findById( idfDashRepository, fiid );
            return mapper.map( entity, dtoClass );
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public List < IDF > findAll() throws DataBaseException, IDFException {
        try {
            List < IDFDashEntity > entities;
            IDFEntityKey key = institutionService.lookupUser();
            if ( key.getFiid().equals( FIID_PROSA ) ) {
                entities = dataBaseService.findByAll( idfDashRepository );
            } else {
                entities = new ArrayList <>();
                entities.add( dataBaseService.findById( idfDashRepository, key.getFiid() ) );
            }
            return mapper.map( entities, listType );
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public List < String > findAllOnlyNames() throws DataBaseException, IDFException {
        List < IDF > idfs = findAll();
        List < String > names = new ArrayList <>();
        for ( IDF idf : idfs ) {
            names.add( idf.getFiid() );
        }
        return names;
    }
}
