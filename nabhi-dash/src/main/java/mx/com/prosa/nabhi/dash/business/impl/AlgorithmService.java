package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IAlgorithmService;
import mx.com.prosa.nabhi.dash.security.UserSession;
import mx.com.prosa.nabhi.misc.domain.algorithm.dto.DispensedAlgorithm;
import mx.com.prosa.nabhi.misc.domain.algorithm.entity.ATDForAlgorithmEntity;
import mx.com.prosa.nabhi.misc.domain.algorithm.entity.DispensedAlgorithmEntity;
import mx.com.prosa.nabhi.misc.domain.algorithm.entity.IDFForAlgorithmEntity;
import mx.com.prosa.nabhi.misc.domain.algorithm.repository.ATDForAlgorithmRepository;
import mx.com.prosa.nabhi.misc.domain.algorithm.repository.DispensedAlgorithmRepository;
import mx.com.prosa.nabhi.misc.domain.group.dto.ATD;
import mx.com.prosa.nabhi.misc.domain.group.dto.Group;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.dash.AlgorithmException;
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
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlgorithmService implements IAlgorithmService {


    private static final String FIID_PROSA = "PROS";
    private static final String THRESHOLDS = "THRESHOLDS";
    private static final String MENOR_NUMERO_DE_BILLETES = "MNB";
    private static final String MENOR_NUMERO_DE_BILLETES_Y_UNO_DE_MENOR_DENOMINACION = "MNB1MD";
    private static final String MENOR_NUMERO_DE_BILLETES_Y_TRES_DE_MENOR_DENOMINACION = "MNB3MD";
    private static final String MIXTO = "MIXTO";
    public static final Logger LOGGER = LoggerFactory.getLogger( AlgorithmService.class );
    private final DispensedAlgorithmRepository dispensedAlgorithmRepository;
    private final UserSession userSession;
    private final ModelMapper mapper;
    private final ATDForAlgorithmRepository atdForAlgorithmRepository;
    private final GroupService groupService;

    @Autowired
    public AlgorithmService( DispensedAlgorithmRepository dispensedAlgorithmRepository, UserSession userSession, ModelMapper mapper, ATDForAlgorithmRepository atdForAlgorithmRepository, GroupService groupService ) {
        this.dispensedAlgorithmRepository = dispensedAlgorithmRepository;
        this.userSession = userSession;
        this.mapper = mapper;
        this.atdForAlgorithmRepository = atdForAlgorithmRepository;
        this.groupService = groupService;
    }

    @Override
    public String create( DispensedAlgorithm algorithm ) throws AlgorithmException {
        DispensedAlgorithmEntity algorithmEntity = mapper.map( algorithm, DispensedAlgorithmEntity.class );
        IDFEntityKey key = lookupUser();
        if ( !key.getFiid().equals( FIID_PROSA ) ) {
            algorithmEntity.setIdf( new IDFForAlgorithmEntity( key.getFiid() ) );
        }
        try {
            algorithmEntity = dispensedAlgorithmRepository.save( algorithmEntity );
            String str = String.format( "Algoritmo de dispensado con id %d y nombre %s creado correctamente", algorithmEntity.getId(), algorithmEntity.getName() );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( str );
            }
            return str;
        } catch ( EntityNotFoundException e ) {
            throw new AlgorithmException( CatalogError.ALGORITHM_NOT_REFERENCE );
        } catch ( DataIntegrityViolationException e ) {
            throw new AlgorithmException( CatalogError.ALGORITHM_DUPLICATE_NAME );
        } catch ( InvalidDataAccessApiUsageException e ) {
            throw new AlgorithmException( CatalogError.ALGORITHM_NOT_REFERENCE_2 );
        }
    }

    @Override
    public String update( DispensedAlgorithm algorithm ) throws AlgorithmException {
        DispensedAlgorithmEntity algorithmEntity = mapper.map( algorithm, DispensedAlgorithmEntity.class );
        try {
            if ( dispensedAlgorithmRepository.findById( algorithmEntity.getId() ).isPresent() ) {
                algorithmEntity = dispensedAlgorithmRepository.save( algorithmEntity );
                String str = String.format( "Algoritmo de dispensado con id %d y nombre %s actualizado correctamente", algorithmEntity.getId(), algorithmEntity.getName() );
                if ( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( str );
                }
                return str;
            } else {
                throw new AlgorithmException( CatalogError.ALGORITHM_UPDATE_NOT_FOUND );
            }
        } catch ( InvalidDataAccessApiUsageException | DataIntegrityViolationException e ) {
            throw new AlgorithmException( CatalogError.ALGORITHM_NOT_REFERENCE_2 );
        } catch ( DataAccessException e ) {
            throw new AlgorithmException( CatalogError.ALGORITHM_NOT_REFERENCE );
        }
    }

    @Override
    public String delete( DispensedAlgorithm algorithm ) throws AlgorithmException {
        DispensedAlgorithmEntity entity = mapper.map( algorithm, DispensedAlgorithmEntity.class );
        try {
            dispensedAlgorithmRepository.delete( entity );
            String str = String.format( "Algoritmo de dispensado con id %d y nombre %s eliminado correctamente", algorithm.getId(), algorithm.getName() );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( str );
            }
            return str;
        } catch ( DataIntegrityViolationException e ) {
            throw new AlgorithmException( CatalogError.ALGORITHM_NOT_REFERENCE_2 );
        } catch ( DataAccessException e ) {
            throw new AlgorithmException( CatalogError.ALGORITHM_NOT_REFERENCE );
        }
    }

    @Override
    public DispensedAlgorithm findByName( String name ) throws AlgorithmException {
        return mapper.map( findByNameEntity( name ), DispensedAlgorithm.class );
    }


    @Override
    public List < DispensedAlgorithm > findAll() throws AlgorithmException {
        IDFEntityKey key = lookupUser();
        List < DispensedAlgorithmEntity > entities;
        if ( key.getFiid().equals( FIID_PROSA ) ) {
            entities = dispensedAlgorithmRepository.findAll();
        } else {
            IDFForAlgorithmEntity idf = new IDFForAlgorithmEntity( key.getFiid() );
            entities = dispensedAlgorithmRepository.findAllByIdf( idf );
            entities.addAll( dispensedAlgorithmRepository.findAllByDispensedTypeIsNot( THRESHOLDS ) );
        }
        if ( !entities.isEmpty() ) {
            Type listType = new TypeToken < List < DispensedAlgorithm > >() {
            }.getType();
            return mapper.map( entities, listType );
        } else {
            throw new AlgorithmException( CatalogError.ALGORITHM_FIND_GROUP_EMPTY_RESULT_SET );
        }
    }

    @Override
    public List < String > findAllOnlyNames() throws AlgorithmException {
        List < DispensedAlgorithm > algorithms = findAll();
        List < String > names = new ArrayList <>();
        for ( DispensedAlgorithm algorithm : algorithms ) {
            names.add( algorithm.getName() );
        }
        return names;
    }

    @Override
    public List < String > findAllOnlyNames( String fiid ) throws AlgorithmException {
        List < DispensedAlgorithmEntity > entities;
        IDFForAlgorithmEntity idf = new IDFForAlgorithmEntity( fiid );
        entities = dispensedAlgorithmRepository.findAllByIdf( idf );
        entities.addAll( dispensedAlgorithmRepository.findAllByDispensedTypeIsNot( THRESHOLDS ) );
        if ( !entities.isEmpty() ) {
            LOGGER.info( "Algoritmos encontrados" );
            List < String > names = new ArrayList <>();
            for ( DispensedAlgorithmEntity algorithm : entities ) {
                names.add( algorithm.getName() );
            }
            return names;
        } else {
            throw new AlgorithmException( CatalogError.ALGORITHM_FIND_GROUP_EMPTY_RESULT_SET );
        }
    }

    @Override
    public String updateGroup( String groupName, String algorithm ) throws AlgorithmException {
        try {
            StringBuilder str = new StringBuilder( String.format( "Algoritmo de dispensado %s ejecutado", algorithm ) );
            Group group = groupService.findByName( groupName );
            DispensedAlgorithmEntity dispensedAlgorithm = findByNameEntity( algorithm );
            for ( ATD atd : group.getAtds() ) {
                if ( !dispensedAlgorithm.getDispensedType().equals( THRESHOLDS ) ) {
                    str.append( updateAtd( atd, dispensedAlgorithm ) );
                } else if ( atd.getIdf().getFiid().equals( dispensedAlgorithm.getIdf().getFiid() ) ) {
                    str.append( updateAtd( atd, dispensedAlgorithm ) );
                } else {
                    str.append( "\n" );
                    str.append( String.format( "Cajero %s no actualizado ya que el FIID del algoritmo no coincide al FIID del grupo", atd.getTerminalId() ) );
                }
            }
            return str.toString();
        } catch ( GroupException e ) {
            throw new AlgorithmException( e.getError() );
        }
    }

    private IDFEntityKey lookupUser() throws AlgorithmException {
        try {
            return userSession.getIDFForCurrentUser();
        } catch ( UserNotFoundException e ) {
            throw new AlgorithmException( e.getError() );
        }
    }

    private DispensedAlgorithmEntity findByNameEntity( String name ) throws AlgorithmException {
        IDFEntityKey key = lookupUser();
        Optional < DispensedAlgorithmEntity > optional;
        if ( !key.getFiid().equals( FIID_PROSA ) ) {
            if ( name.equals( MENOR_NUMERO_DE_BILLETES ) ||
                    name.equals( MENOR_NUMERO_DE_BILLETES_Y_UNO_DE_MENOR_DENOMINACION ) ||
                    name.equals( MENOR_NUMERO_DE_BILLETES_Y_TRES_DE_MENOR_DENOMINACION ) ||
                    name.equals( MIXTO ) ) {
                optional = dispensedAlgorithmRepository.findByName( name );
            } else {
                IDFForAlgorithmEntity idf = new IDFForAlgorithmEntity( key.getFiid() );
                optional = dispensedAlgorithmRepository.findByNameAndIdf( name, idf );
            }
        } else {
            optional = dispensedAlgorithmRepository.findByName( name );
        }
        if ( optional.isPresent() ) {
            return optional.get();
        }
        throw new AlgorithmException( CatalogError.ALGORITHM_FIND_GROUP_EMPTY_RESULT );
    }

    private String updateAtd( ATD atd, DispensedAlgorithmEntity dispensedAlgorithm ) {
        Optional < ATDForAlgorithmEntity > optional = atdForAlgorithmRepository.findById( atd.getTerminalId() );
        if ( optional.isPresent() ) {
            ATDForAlgorithmEntity entity = optional.get();
            entity.setDispensedAlgorithm( dispensedAlgorithm );
            atdForAlgorithmRepository.save( entity );
            return "";
        }
        return System.lineSeparator() + String.format( "Cajero %s no actualizado ya que no existe o ya no pertenece al grupo de cajeros para algoritmos", atd.getTerminalId() );
    }
}
