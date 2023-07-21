package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IScreenService;
import mx.com.prosa.nabhi.dash.core.IDataBaseService;
import mx.com.prosa.nabhi.dash.core.IInstitutionService;
import mx.com.prosa.nabhi.misc.domain.complete.entity.ScreenGroupEntity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.ScreenGroupRepository;
import mx.com.prosa.nabhi.misc.domain.group.dto.ATD;
import mx.com.prosa.nabhi.misc.domain.group.dto.Group;
import mx.com.prosa.nabhi.misc.domain.personalized.entity.ATDScreenEntity;
import mx.com.prosa.nabhi.misc.domain.personalized.repository.ATDScreenRepository;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.dash.GroupException;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.ScreenGroup;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScreenService implements IScreenService {

    private final ScreenGroupRepository screenGroupRepository;
    private final ModelMapper mapper;
    private final IDataBaseService dataBaseService;
    private final IInstitutionService institutionService;
    private final ATDScreenRepository atdScreenRepository;
    private final GroupService groupService;
    private static final Logger LOGGER = LoggerFactory.getLogger( ScreenService.class );
    private static final String FIID_PROSA = "PROS";
    private static final String TABLE = "PAYLOAD";
    private static final String ERROR = "para el modulo " + TABLE;
    private static final Class < ScreenGroupEntity > domainClass = ScreenGroupEntity.class;
    private static final Class < ScreenGroup > dtoClass = ScreenGroup.class;
    private final Type listType = new TypeToken < List < ScreenGroup > >() {
    }.getType();

    @Autowired
    public ScreenService( ScreenGroupRepository screenGroupRepository, ModelMapper mapper, IDataBaseService dataBaseService, IInstitutionService institutionService, ATDScreenRepository atdScreenRepository, GroupService groupService ) {
        this.screenGroupRepository = screenGroupRepository;
        this.mapper = mapper;
        this.dataBaseService = dataBaseService;
        this.institutionService = institutionService;
        this.atdScreenRepository = atdScreenRepository;
        this.groupService = groupService;
    }

    @Override
    public String create( ScreenGroup value ) throws DataBaseException {
        try {
            ScreenGroupEntity entity = mapper.map( value, domainClass );
            dataBaseService.create( screenGroupRepository, entity.getGroupId(), entity );
            return "Grupo de pantallas Creado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public String modify( ScreenGroup value ) throws DataBaseException {
        ScreenGroupEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.modify( screenGroupRepository, entity.getGroupId(), entity );
            return "Grupo de pantallas Modificado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public String delete( ScreenGroup value ) throws DataBaseException, IDFException {
        ScreenGroupEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.delete( screenGroupRepository, entity.getGroupId() );
            return "Grupo de pantallas Eliminado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public ScreenGroup findById( String name ) throws DataBaseException {
        try {
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( "Buscando Imagen con llave %s", name ) );
            }
            ScreenGroupEntity entity = dataBaseService.findById( screenGroupRepository, name );
            return mapper.map( entity, dtoClass );
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public List < ScreenGroup > findAll() throws DataBaseException, IDFException {
        try {
            List < ScreenGroupEntity > entities;
            IDFEntityKey key = institutionService.lookupUser();
            if ( key.getFiid().equals( FIID_PROSA ) ) {
                entities = dataBaseService.findByAll( screenGroupRepository );
            } else {
                entities = screenGroupRepository.findAllByFiid( key.getFiid() );
            }
            if ( !entities.isEmpty() ) {
                return mapper.map( entities, listType );
            } else {
                throw new DataBaseException( CatalogError.SCREEN_FIND_GROUP_EMPTY_RESULT_SET );
            }
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }

    }

    @Override
    public List < String > findAllOnlyNames() throws DataBaseException, IDFException {
        List < ScreenGroup > groups = findAll();
        List < String > names = new ArrayList <>();
        for ( ScreenGroup screenGroup : groups ) {
            names.add( screenGroup.getGroupId() );
        }
        return names;
    }

    @Override
    public List < String > findAllOnlyNames( String fiid ) throws DataBaseException, IDFException {
        List < ScreenGroupEntity > entities = screenGroupRepository.findAllByFiid( fiid );
        List < String > names = new ArrayList <>();
        for ( ScreenGroupEntity screenGroup : entities ) {
            names.add( screenGroup.getGroupId() );
        }
        if ( names.isEmpty() ) {
            throw new DataBaseException( CatalogError.SCREEN_FIND_GROUP_EMPTY_RESULT_SET );
        }
        return names;
    }

    @Override
    public String updateGroup( String groupName, String screen ) throws DataBaseException, IDFException {
        try {
            StringBuilder str = new StringBuilder( String.format( "Grupo de pantallas %s ejecutada", screen ) );
            Group group = groupService.findByName( groupName );
            ScreenGroupEntity entity = findByNameEntity( screen );
            for ( ATD atd : group.getAtds() ) {
                str.append( updateAtd( atd, entity ) );
            }
            return str.toString();
        } catch ( GroupException e ) {
            throw new DataBaseException( e.getError() );
        }
    }

    private ScreenGroupEntity findByNameEntity( String name ) throws IDFException, DataBaseException {
        IDFEntityKey key = institutionService.lookupUser();
        Optional < ScreenGroupEntity > optional;
        if ( !key.getFiid().equals( FIID_PROSA ) ) {
            optional = screenGroupRepository.findByFiidAndGroupId( key.getFiid(), name );
        } else {
            optional = screenGroupRepository.findById( name );
        }
        if ( optional.isPresent() ) {
            return optional.get();
        }
        throw new DataBaseException( CatalogError.SCREEN_FIND_GROUP_EMPTY_RESULT );
    }

    private String updateAtd( ATD atd, ScreenGroupEntity screenGroupEntity ) {
        Optional < ATDScreenEntity > optional = atdScreenRepository.findById( atd.getTerminalId() );
        if ( optional.isPresent() ) {
            ATDScreenEntity entity = optional.get();
            entity.setScreenGroup( screenGroupEntity );
            atdScreenRepository.save( entity );
            return "";
        } else {
            return System.lineSeparator() + String.format( "Cajero %s no actualizado ya que no existe o ya no pertenece al grupo de cajeros", atd.getTerminalId() );
        }
    }
}
