package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IGroupService;
import mx.com.prosa.nabhi.dash.model.group.CriteriaSearch;
import mx.com.prosa.nabhi.dash.security.UserSession;
import mx.com.prosa.nabhi.misc.domain.group.dto.*;
import mx.com.prosa.nabhi.misc.domain.group.entity.*;
import mx.com.prosa.nabhi.misc.domain.group.repository.*;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.dash.GroupException;
import mx.com.prosa.nabhi.misc.exception.dash.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements IGroupService {

    private static final String FIID_PROSA = "PROS";
    private static final Logger LOGGER = LoggerFactory.getLogger( GroupService.class );

    private final GroupRepository groupRepository;
    private final ModelMapper mapper;
    private final UserSession userSession;
    private final CountyForGroupRepository countyForGroupRepository;
    private final StateForGroupRepository stateForGroupRepository;
    private final IDFForGroupRepository idfForGroupRepository;
    private final ATDForGroupRepository atdForGroupRepository;

    @Autowired
    public GroupService( GroupRepository groupRepository, ModelMapper mapper, UserSession userSession, CountyForGroupRepository countyForGroupRepository, StateForGroupRepository stateForGroupRepository, IDFForGroupRepository idfForGroupRepository, ATDForGroupRepository atdForGroupRepository ) {
        this.groupRepository = groupRepository;
        this.mapper = mapper;
        this.userSession = userSession;
        this.countyForGroupRepository = countyForGroupRepository;
        this.stateForGroupRepository = stateForGroupRepository;
        this.idfForGroupRepository = idfForGroupRepository;
        this.atdForGroupRepository = atdForGroupRepository;
    }

    @Override
    public String create( Group group ) throws GroupException {
        GroupEntity groupEntity = mapper.map( group, GroupEntity.class );
        IDFEntityKey key = lookupUser();
        if ( !key.getFiid().equals( FIID_PROSA ) ) {
            groupEntity.setIdf( new IDFForGroupEntity( key.getFiid() ) );
        }
        try {
            groupEntity = groupRepository.save( groupEntity );
            String str = String.format( "Grupo de cajeros con id %d y nombre %s creado correctamente", groupEntity.getId(), groupEntity.getName() );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( str );
            }
            return str;
        } catch ( EntityNotFoundException e ) {
            throw new GroupException( CatalogError.GROUP_NOT_REFERENCE );
        }catch ( DataIntegrityViolationException e ){
            throw new GroupException( CatalogError.GROUP_DUPLICATE_NAME );
        } catch ( InvalidDataAccessApiUsageException e ) {
            throw new GroupException( CatalogError.GROUP_NOT_REFERENCE_2 );
        }
    }

    @Override
    public String update( Group group ) throws GroupException {
        GroupEntity groupEntity = mapper.map( group, GroupEntity.class );
        try {
            if ( groupRepository.findById( groupEntity.getId() ).isPresent() ) {
                groupEntity = groupRepository.save( groupEntity );
                String str = String.format( "Grupo de cajeros con id %d y nombre %s actualizado correctamente", groupEntity.getId(), groupEntity.getName() );
                if ( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( str );
                }
                return str;
            } else {
                throw new GroupException( CatalogError.GROUP_UPDATE_NOT_FOUND );
            }
        } catch ( InvalidDataAccessApiUsageException | DataIntegrityViolationException e ) {
            throw new GroupException( CatalogError.GROUP_NOT_REFERENCE_2 );
        } catch ( DataAccessException e ) {
            throw new GroupException( CatalogError.GROUP_NOT_REFERENCE );
        }
    }

    @Override
    public String delete( Group group ) throws GroupException {
        GroupEntity entity = mapper.map( group, GroupEntity.class );
        try {
            groupRepository.delete( entity );
            String str = String.format( "Grupo de cajeros con id %d y nombre %s eliminado correctamente", group.getId(), group.getName() );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( str );
            }
            return str;
        } catch ( DataIntegrityViolationException e ) {
            throw new GroupException( CatalogError.GROUP_NOT_REFERENCE_2 );
        } catch ( DataAccessException e ) {
            throw new GroupException( CatalogError.GROUP_NOT_REFERENCE );
        }
    }

    @Override
    public Group findByName( String name ) throws GroupException {
        IDFEntityKey key = lookupUser();
        Optional < GroupEntity > optional;
        if ( !key.getFiid().equals( FIID_PROSA ) ) {
            IDFForGroupEntity idf = new IDFForGroupEntity( key.getFiid() );
            optional = groupRepository.findByNameAndIdf( name, idf );
        } else {
            optional = groupRepository.findByName( name );
        }
        if ( optional.isPresent() ) {
            return mapper.map( optional.get(), Group.class );
        }
        throw new GroupException( CatalogError.GROUP_FIND_GROUP_EMPTY_RESULT );
    }


    @Override
    public List< String > findATDSNames( String name ) throws GroupException {
        List< String > atds = new ArrayList <>();
        for ( ATD atd : findByName( name ).getAtds() ){
            atds.add( atd.getTerminalId() );
        }
        return atds;
    }

    @Override
    public List < Group > findAll() throws GroupException {
        IDFEntityKey key = lookupUser();
        List < GroupEntity > entities;
        if ( key.getFiid().equals( FIID_PROSA ) ) {
            entities = groupRepository.findAll();
        } else {
            IDFForGroupEntity idf = new IDFForGroupEntity( key.getFiid() );
            entities = groupRepository.findAllByIdf( idf );
        }
        if ( !entities.isEmpty() ) {
            Type listType = new TypeToken < List < Group > >() {
            }.getType();
            return mapper.map( entities, listType );
        } else {
            throw new GroupException( CatalogError.GROUP_FIND_GROUP_EMPTY_RESULT_SET );
        }
    }

    @Override
    public List < String > findAllOnlyNames() throws GroupException {
        List < Group > groups = findAll();
        List < String > names = new ArrayList <>();
        for ( Group group : groups ) {
            names.add( group.getName() );
        }
        return names;
    }

    @Override
    public List < IDF > findIDFS() throws GroupException {
        List < IDFForGroupEntity > entities = idfForGroupRepository.findAll();
        if ( !entities.isEmpty() ) {
            Type listType = new TypeToken < List < IDF > >() {
            }.getType();
            entities.removeIf( e -> e.getFiid().equals( FIID_PROSA ) );
            return mapper.map( entities, listType );
        } else {
            throw new GroupException( CatalogError.GROUP_FIND_IDF_EMPTY_RESULT_SET );
        }
    }

    @Override
    public List < State > findStates() throws GroupException {
        List < StateForGroupEntity > entities = stateForGroupRepository.findAll();
        if ( !entities.isEmpty() ) {
            Type listType = new TypeToken < List < State > >() {
            }.getType();
            return mapper.map( entities, listType );
        } else {
            throw new GroupException( CatalogError.GROUP_FIND_STATE_EMPTY_RESULT_SET );
        }
    }

    @Override
    public List < County > findCountiesByState( State state ) throws GroupException {
        StateForGroupEntity stateForGroupEntity = mapper.map( state, StateForGroupEntity.class );
        List < CountyForGroupEntity > entities = countyForGroupRepository.findAllByState( stateForGroupEntity );
        if ( !entities.isEmpty() ) {
            Type listType = new TypeToken < List < County > >() {
            }.getType();
            return mapper.map( entities, listType );
        } else {
            throw new GroupException( CatalogError.GROUP_FIND_COUNTY_EMPTY_RESULT_SET );
        }
    }

    @Override
    public List < ATD > findByCriteria( CriteriaSearch criteriaSearch ) throws GroupException {
        IDFEntityKey key = lookupUser();
        if ( !key.getFiid().equals( FIID_PROSA ) ) {
            criteriaSearch.setFiid( key.getFiid() );
        }
        List < ATDForGroupEntity > entities = new ArrayList <>();
        if ( criteriaSearch.getTerminalId() != null && !criteriaSearch.getTerminalId().isEmpty() ) {
            Optional < ATDForGroupEntity > optional;
            if ( !criteriaSearch.getFiid().equals( FIID_PROSA ) ) {
                IDFForGroupEntity idfForGroupEntity = new IDFForGroupEntity( criteriaSearch.getFiid() );
                optional = atdForGroupRepository.findByTerminalIdAndIdf( criteriaSearch.getTerminalId(), idfForGroupEntity );
            } else {
                optional = atdForGroupRepository.findById( criteriaSearch.getTerminalId() );
            }
            if ( optional.isPresent() ) {
                entities.add( optional.get() );
            } else {
                throw new GroupException( CatalogError.GROUP_FIND_ATD_EMPTY_RESULT );
            }
        } else {
            Pageable pageable = getPageableRequest( criteriaSearch.getPage(), criteriaSearch.getResults() );
            Page < ATDForGroupEntity > pages = atdForGroupRepository.findAll(
                    Specification.where( fiidIs( criteriaSearch.getFiid() )
                            .and( countyByStateIs( criteriaSearch ) )
                            .and( deviceTypeIs( criteriaSearch.getDeviceType() ) )
                            .and( screenTypeIs( criteriaSearch.getScreenType() ) )
                    ), pageable );
            entities = pages.getContent();
        }
        if ( !entities.isEmpty() ) {
            Type listType = new TypeToken < List < ATD > >() {
            }.getType();
            return mapper.map( entities, listType );
        } else {
            throw new GroupException( CatalogError.GROUP_FIND_ATD_EMPTY_RESULT_SET );
        }
    }


    private IDFEntityKey lookupUser() throws GroupException {
        try {
            return userSession.getIDFForCurrentUser();
        } catch ( UserNotFoundException e ) {
            throw new GroupException( e.getError() );
        }
    }

    private Pageable getPageableRequest( int page, int results ) {
        if ( results == 0 ) {
            return PageRequest.of( page, 10 );
        } else {
            return PageRequest.of( page, results );
        }
    }

    private int[] getCounties( String stateCode ) throws GroupException {
        int[] counties;
        State state = new State();
        state.setStateCode( stateCode );
        List < County > countyList = findCountiesByState( state );
        counties = new int[ countyList.size() ];
        for ( int i = 0; i < counties.length; i++ ) {
            counties[ i ] = countyList.get( i ).getCountyCodeId();
        }
        return counties;
    }


    private Specification < ATDForGroupEntity > fiidIs( String fiid ) {
        IDFForGroupEntity entity = new IDFForGroupEntity();
        entity.setFiid( fiid );
        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( String.format( "Cuando fiid %s", fiid ) );
        }
        return ( ( root, criteriaQuery, criteriaBuilder ) -> criteriaBuilder.equal( root.get( "idf" ), entity ) );
    }

    private Specification < ATDForGroupEntity > countyByStateIs( CriteriaSearch criteriaSearch ) throws GroupException {
        if ( criteriaSearch.getStateCode() == null || criteriaSearch.getStateCode().isEmpty() ) {
            return countyIs( criteriaSearch.getCountyCode() );
        }
        int[] counties = getCounties( criteriaSearch.getStateCode() );
        return ( root, query, criteriaBuilder ) -> {
            List < Predicate > predicates = new ArrayList <>();
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "Y" );
            }
            for ( int county : counties ) {
                CountyForGroupEntity entity = new CountyForGroupEntity();
                entity.setCountyCodeId( county );
                if ( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( String.format( "O Cuando county %d", county ) );
                }
                Predicate predicate = criteriaBuilder.equal( root.get( "county" ), entity );
                predicates.add( predicate );

            }
            return criteriaBuilder.or( predicates.toArray( new Predicate[ 0 ] ) );
        };
    }

    private Specification < ATDForGroupEntity > countyIs( int countyCode ) {
        if ( countyCode == 0 ) {
            return ( root, query, criteriaBuilder ) -> criteriaBuilder.conjunction();
        }
        CountyForGroupEntity entity = new CountyForGroupEntity();
        entity.setCountyCodeId( countyCode );
        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( String.format( "Y Cuando county %d", countyCode ) );
        }
        return ( root, query, criteriaBuilder ) -> criteriaBuilder.equal( root.get( "county" ), entity );
    }

    private Specification < ATDForGroupEntity > screenTypeIs( String screenType ) {
        return ( root, query, criteriaBuilder ) -> {
            if ( screenType == null || screenType.isEmpty() ) {
                return criteriaBuilder.conjunction();
            }
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( "Y Cuando screenType %s", screenType ) );
            }
            return criteriaBuilder.equal( root.get( "screenType" ), screenType );
        };
    }

    private Specification < ATDForGroupEntity > deviceTypeIs( String deviceType ) {
        return ( root, query, criteriaBuilder ) -> {
            if ( deviceType == null  || deviceType.isEmpty() ) {
                return criteriaBuilder.conjunction();
            }
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( "Y Cuando deviceType %s", deviceType ) );
            }
            return criteriaBuilder.equal( root.get( "deviceType" ), deviceType );
        };
    }

}
