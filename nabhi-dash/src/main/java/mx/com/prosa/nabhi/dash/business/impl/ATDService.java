package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IATDService;
import mx.com.prosa.nabhi.dash.core.IDataBaseService;
import mx.com.prosa.nabhi.dash.core.IInstitutionService;
import mx.com.prosa.nabhi.dash.security.ldap.LDAPException;
import mx.com.prosa.nabhi.misc.domain.personalized.entity.ATDDashEntity;
import mx.com.prosa.nabhi.misc.domain.personalized.repository.ATDDashRepository;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.exception.sanitize.SanitazeException;
import mx.com.prosa.nabhi.misc.exception.user.AccessControlException;
import mx.com.prosa.nabhi.misc.model.jdb.ATD;
import mx.com.prosa.nabhi.misc.model.jdb.Role;
import mx.com.prosa.nabhi.misc.model.jdb.User;
import mx.com.prosa.nabhi.misc.model.jdb.onlykeys.IDFKey;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ATDService implements IATDService {

    private final ATDDashRepository atdRepository;
    private final ModelMapper mapper;
    private final IDataBaseService dataBaseService;
    private final IInstitutionService institutionService;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger( ATDService.class );
    private static final String FIID_PROSA = "PROS";
    private static final String TABLE = "CAJEROS";
    private static final String ERROR = "para el modulo " + TABLE;
    private static final Class < ATDDashEntity > domainClass = ATDDashEntity.class;
    private static final Class < ATD > dtoClass = ATD.class;
    private final Type listType = new TypeToken < List < ATD > >() {
    }.getType();

    @Autowired
    public ATDService( ATDDashRepository atdRepository, ModelMapper mapper, IDataBaseService dataBaseService, IInstitutionService institutionService, UserService userService ) {
        this.atdRepository = atdRepository;
        this.mapper = mapper;
        this.dataBaseService = dataBaseService;
        this.institutionService = institutionService;
        this.userService = userService;
    }

    @Override
    public String create( ATD value ) throws DataBaseException {
        try {
            ATDDashEntity entity = mapper.map( value, domainClass );
            dataBaseService.create( atdRepository, entity.getTerminalId(), entity );
            User user = addATMUser( value );
            userService.create( user, true );
            return "ATD Creado correctamente";
        } catch ( DataBaseException | AccessControlException | SanitazeException | LDAPException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public String modify( ATD value ) throws DataBaseException {
        ATDDashEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.modify( atdRepository, entity.getTerminalId(), entity );
            User user = addATMUser( value );
            userService.modify( user, true );
            return "ATD Modificado correctamente";
        } catch ( DataBaseException | AccessControlException | SanitazeException | LDAPException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public String delete( ATD value ) throws DataBaseException, IDFException {
        ATDDashEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.delete( atdRepository, entity.getTerminalId() );
            User user = addATMUser( value );
            userService.delete( user, true );
            return "ATD Eliminado correctamente";
        } catch ( DataBaseException | AccessControlException | LDAPException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public ATD findById( String terminalId ) throws DataBaseException {
        try {
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( "Buscando ATD con llave %s ", terminalId ) );
            }
            ATDDashEntity entity = dataBaseService.findById( atdRepository, terminalId );
            return mapper.map( entity, dtoClass );
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public List < ATD > findAll() throws DataBaseException, IDFException {
        try {
            List < ATDDashEntity > entities;
            IDFEntityKey key = institutionService.lookupUser();
            if ( key.getFiid().equals( FIID_PROSA ) ) {
                entities = dataBaseService.findByAll( atdRepository );
            } else {
                entities = atdRepository.findAllByIdfEquals( key );
            }
            return mapper.map( entities, listType );
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }

    }

    @Override
    public List < String > findAllOnlyNames() throws DataBaseException, IDFException {
        List < ATD > atds = findAll();
        List < String > names = new ArrayList <>();
        for ( ATD atd : atds ) {
            names.add( atd.getTerminalId() );
        }
        return names;
    }

    private User addATMUser( ATD atd ) {
        long now = new Date().getTime() / 1000;
        User user = new User();
        user.setId( atd.getTerminalId() );
        user.setP( atd.getIp() );
        user.setIssueTime( now );
        user.setExpirationTime( now + 31536000 );
        user.setExpirationTokenTime( 86400 );
        user.setOwner( new IDFKey( atd.getIdf().getFiid() ) );
        String pack = atd.getPackageName().getName();
        String role;
        if ( pack.equals( "BASICO" ) ) {
            role = "ATM_BASICO";
        } else if ( pack.equals( "ADVANCE" ) ) {
            role = "ATM_ADVANCE";
        } else {
            role = "ATM_FULL";
        }
        user.setRoles( Collections.singletonList( new Role( role ) ) );
        return user;
    }
}
