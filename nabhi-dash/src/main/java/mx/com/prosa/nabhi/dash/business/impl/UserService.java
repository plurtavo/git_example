package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IUserService;
import mx.com.prosa.nabhi.dash.core.IDataBaseService;
import mx.com.prosa.nabhi.dash.security.ldap.LDAPException;
import mx.com.prosa.nabhi.dash.security.ldap.LDAPService;
import mx.com.prosa.nabhi.dash.security.ldap.LdapUser;
import mx.com.prosa.nabhi.misc.domain.log.entity.LogEventEntity;
import mx.com.prosa.nabhi.misc.domain.log.repository.LogEventRepository;
import mx.com.prosa.nabhi.misc.domain.security.entity.*;
import mx.com.prosa.nabhi.misc.domain.security.repository.*;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.sanitize.SanitazeException;
import mx.com.prosa.nabhi.misc.exception.user.AccessControlException;
import mx.com.prosa.nabhi.misc.model.jdb.*;
import mx.com.prosa.nabhi.misc.util.SanitizeModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class UserService implements IUserService {

    //Cambio release/eventos
    private static final String APP_SOCKET = "APP_SOCKET";
    private static final String APP_TRANSACTIONAL = "APP_TRANSACTIONAL";
    private static final String ROLE_ATM = "ATM";
    private static final String ATM_BASICO = "ATM_BASICO";
    private static final String ATM_ADVANCE = "ATM_ADVANCE";
    private static final String ATM_FULL = "ATM_FULL";
    //Cambio release/eventos
    private final UserRepository userRepository;
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
    private final LDAPService ldapService;
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
    private final SanitizeModel cleanCode;
    private static final Logger LOGGER = LoggerFactory.getLogger( UserService.class );
    //Cambio release/eventos
    private final PrivilegeRepository privilegeRepository;
    private MenuRepository menuRepository;
    private RoleMenuRepository roleMenuRepository;
    private IDataBaseService baseService;
    private LogEventRepository logEventRepository;
    private static final String PATTERN = "yyyy-MM-dd hh:mm:ss";

    @Autowired
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
    public UserService( UserRepository userRepository, LDAPService ldapService, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, ModelMapper mapper, SanitizeModel cleanCode, PrivilegeRepository privilegeRepository ) {
        //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
        this.userRepository = userRepository;
        this.ldapService = ldapService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.cleanCode = cleanCode;
        this.privilegeRepository = privilegeRepository;
    }

    @Autowired
    public void setMenuRepository( MenuRepository menuRepository ) {
        this.menuRepository = menuRepository;
    }

    @Autowired
    public void setRoleMenuRepository( RoleMenuRepository roleMenuRepository ) {
        this.roleMenuRepository = roleMenuRepository;
    }

    @Autowired
    public void setBaseService( IDataBaseService baseService ) {
        this.baseService = baseService;
    }

    @Autowired
    public void setLogEventRepository( LogEventRepository logEventRepository ) {
        this.logEventRepository = logEventRepository;
    }

    //Cambio release/eventos

    @Override
    public String create( User tokenUser, boolean isAtmOrService ) throws AccessControlException, SanitazeException, LDAPException {
        userExist( tokenUser.getId() );
        verifyAndUpdate( isAtmOrService, tokenUser );
        return "Usuario creado correctamente";
    }

    @Override
    public String modify( User tokenUser, boolean isAtmOrService ) throws AccessControlException, SanitazeException, LDAPException {
        Optional < UserEntity > oe = userRepository.findById( tokenUser.getId() );
        if ( oe.isPresent() ) {
            verifyAndUpdate( isAtmOrService, tokenUser );
            return "Usuario modificado correctamente";
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.USER_ERROR_NOT_FOUND.getMessage() );
            }
            throw new AccessControlException( CatalogError.LDAP_ERROR_USER_NOT_FOUND_DATA );
        }
    }

    @Override
    public String delete( User tokenUser, boolean isAtmOrService ) throws AccessControlException, LDAPException {
        Optional < UserEntity > entity = userRepository.findById( tokenUser.getId() );
        if ( entity.isPresent() ) {
            if ( !isAtmOrService ) {
                //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
                ldapService.findUser( tokenUser.getId() );
                throw new AccessControlException( CatalogError.USER_ALREADY_EXIST_NOT_DELETE );
                //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
            }
            userRepository.delete( entity.get() );
            return "Usuario eliminado correctamente";
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.USER_ERROR_NOT_FOUND.getMessage() );
            }
            throw new AccessControlException( CatalogError.LDAP_ERROR_USER_NOT_FOUND_DATA );
        }
    }

    @Override
    public String enable( User tokenUser ) throws AccessControlException {
        Optional < UserEntity > entity = userRepository.findById( tokenUser.getId() );
        if ( entity.isPresent() ) {
            entity.get().setEnabled( true );
            userRepository.save( entity.get() );
            return "Usuario Habilitado";
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.USER_ERROR_NOT_FOUND.getMessage() );
            }
            throw new AccessControlException( CatalogError.LDAP_ERROR_USER_NOT_FOUND_DATA );
        }
    }

    @Override
    public String disable( User tokenUser ) throws AccessControlException {
        Optional < UserEntity > entity = userRepository.findById( tokenUser.getId() );
        if ( entity.isPresent() ) {
            entity.get().setEnabled( false );
            userRepository.save( entity.get() );
            return "Usuario Deshabilitado";
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.USER_ERROR_NOT_FOUND.getMessage() );
            }
            throw new AccessControlException( CatalogError.LDAP_ERROR_USER_NOT_FOUND_DATA );
        }
    }

    @Override
    public List < Role > getRoles() throws AccessControlException {
        List < RoleEntity > entities = roleRepository.findAll();
        Type listType = new TypeToken < List < Role > >() {
        }.getType();
        if ( !entities.isEmpty() ) {
            //Cambio release/eventos
            List < RoleEntity > roleEntities = verifyRoleList( entities );
            return mapper.map( roleEntities, listType );
            //Cambio release/eventos
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.ROLES_LIST_EMPTY.toString() );
            }
            throw new AccessControlException( CatalogError.ROLES_LIST_EMPTY );
        }
    }


    private void userExist( String email ) throws AccessControlException {
        Optional < UserEntity > userEntity = userRepository.findById( email );
        if ( userEntity.isPresent() ) {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.USER_ERROR_ALREADY_EXISTS.toString() );
            }
            throw new AccessControlException( CatalogError.USER_ALREADY_EXIST );
        }
    }

    private UserEntity populate( User tokenUser, boolean isATMOrService ) throws SanitazeException {
        cleanCode.sanitize( tokenUser );
        UserEntity userEntity = new UserEntity();
        userEntity.setId( tokenUser.getId() );
        userEntity.setName( tokenUser.getName() );
        userEntity.setLastName( tokenUser.getLastName() );
        userEntity.setEnabled( true );
        userEntity.setTokenExpired( false );
        userEntity.setPhoneNumber( tokenUser.getPhoneNumber() );
        if ( isATMOrService ) {
            userEntity.setPassword( passwordEncoder.encode( tokenUser.getP() ) );
            Set < RoleEntity > roles = new HashSet <>();
            for ( Role role : tokenUser.getRoles() ) {
                roles.add( mapper.map( role, RoleEntity.class ) );
            }
            userEntity.setRoles( roles );
        }
        userEntity.setExpirationTokenTime( tokenUser.getExpirationTokenTime() );
        userEntity.setIssueTime( tokenUser.getIssueTime() );
        userEntity.setExpirationTime( tokenUser.getExpirationTime() );
        userEntity.setLastUsage( tokenUser.getLastUsage() );
        IDFEntityKey entityKey = new IDFEntityKey();
        entityKey.setFiid( tokenUser.getOwner().getFiid() );
        userEntity.setOwner( entityKey );
        return userEntity;
    }

    private void updateRoles( LdapUser user, UserEntity entity ) {
        Set < RoleEntity > roles = new HashSet <>();
        for ( String rol : user.getRoles() ) {
            //Cambio release/eventos
            roles.add( new RoleEntity( rol.toUpperCase() ) );
            //Cambio release/eventos
        }
        entity.setRoles( roles );
    }

    private void verifyAndUpdate( boolean isAtmOrService, User tokenUser ) throws SanitazeException, LDAPException {
        UserEntity entity = populate( tokenUser, isAtmOrService );
        if ( !isAtmOrService ) {
            //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
            LdapUser user = ldapService.findUser( tokenUser.getId() );
            //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
            updateRoles( user, entity );
            entity.setPassword( null );
        }
        userRepository.save( entity );
    }

    @Override
    public List < User > findAll() throws AccessControlException {
        List < UserEntity > entities = userRepository.findAll();
        if ( !entities.isEmpty() ) {
            Type listType = new TypeToken < List < User > >() {
            }.getType();
            //Cambio release/eventos
            List < UserEntity > user = new ArrayList <>();
            populateListUser( entities, user );
            return mapper.map( user, listType );
            //Cambio release/eventos
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.USER_LIST_EMPTY.toString() );
            }
            throw new AccessControlException( CatalogError.USER_LIST_EMPTY );
        }
    }

    private void populateListUser( List < UserEntity > entities, List < UserEntity > user ) {
        for ( UserEntity entity : entities ) {
            for ( RoleEntity roleEntity : entity.getRoles() ) {
                if ( !( roleEntity.getName().equals( APP_SOCKET ) || roleEntity.getName().equals( APP_TRANSACTIONAL )
                        || roleEntity.getName().equals( ATM_BASICO ) || roleEntity.getName().equals( ATM_ADVANCE )
                        || roleEntity.getName().equals( ATM_FULL ) || roleEntity.getName().equals( ROLE_ATM ) ) && !user.contains( entity ) ) {
                    user.add( entity );
                }
            }
        }
    }

    @Override
    public List < String > findAllNames() throws AccessControlException {
        List < User > users = findAll();
        List < String > names = new ArrayList <>();
        for ( User user : users ) {
            names.add( user.getId() );
        }
        return names;
    }

    @Override
    public User findById( String id ) throws AccessControlException {
        Optional < UserEntity > entity = userRepository.findById( id );
        if ( entity.isPresent() ) {
            User user = mapper.map( entity.get(), User.class );
            user.setP( "" );
            return user;
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( String.format( CatalogError.LDAP_ERROR_USER_NOT_FOUND_DATA.toString(), id ) );
            }
            throw new AccessControlException( CatalogError.LDAP_ERROR_USER_NOT_FOUND_DATA );
        }
    }

    //Cambio release/eventos
    @Override
    public List < Privilege > getPrivileges() throws AccessControlException {
        List < PrivilegeEntity > entities = privilegeRepository.findAll();
        Type listType = new TypeToken < List < Privilege > >() {
        }.getType();
        if ( !entities.isEmpty() ) {
            return mapper.map( entities, listType );
        } else {
            throw new AccessControlException( CatalogError.PRIVILEGE_LIST_EMPTY );
        }
    }

    @Override
    public String deleteMenu( MenuObject menu ) throws AccessControlException {
        if ( menu.getId() == 0 ) {
            Optional < MenuEntity > optional = menuRepository.findByMenu( menu.getMenu() );
            optional.ifPresent( menuEntity -> menu.setId( menuEntity.getId() ) );
        }
        try {
            MenuEntity entity = mapper.map( menu, MenuEntity.class );
            menuRepository.delete( entity );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "Menu elminiado exitosamente" );
            }
        } catch ( DataIntegrityViolationException e ) {
            LOGGER.debug( String.format( "El menu %s tiene una restricción de integridad con algún Rol", menu.getMenu() ) );
            throw new AccessControlException( CatalogError.MENU_INTEGRITY );
        }
        return "Menus eliminados exitosamente";
    }

    @Override
    public String addMenus( List < MenuObject > menus ) {
        for ( MenuObject menu : menus ) {
            if ( menu.getId() == 0 ) {
                Optional < MenuEntity > optional = menuRepository.findByMenu( menu.getMenu() );
                optional.ifPresent( menuEntity -> menu.setId( menuEntity.getId() ) );
            }
            try {
                MenuEntity entity = mapper.map( menu, MenuEntity.class );
                menuRepository.save( entity );
            } catch ( DataIntegrityViolationException e ) {
                LOGGER.debug( String.format( "El menu %s ya existe", menu.getMenu() ) );
            }
        }
        return "Menus actualizados exitosamente";
    }

    @Override
    public List < MenuObject > getMenus() throws AccessControlException {
        List < MenuEntity > entities = menuRepository.findAll();
        Type listType = new TypeToken < List < MenuObject > >() {
        }.getType();
        if ( !entities.isEmpty() ) {
            return mapper.map( entities, listType );
        } else {
            throw new AccessControlException( CatalogError.MENU_LIST_EMPTY );
        }
    }

    @Override
    public String updateRoleMenu( Role role ) throws AccessControlException {
        Optional < RoleMenuEntity > optional = roleMenuRepository.findById( role.getName() );
        if ( optional.isPresent() ) {
            Type listType = new TypeToken < List < MenuEntity > >() {
            }.getType();
            List < MenuEntity > entities = mapper.map( role.getMenus(), listType );
            Set < MenuEntity > menus = new HashSet <>( entities );
            RoleMenuEntity entity = optional.get();
            entity.setMenus( menus );
            roleMenuRepository.save( entity );
            return "Se asociaron correctamente los menus al Rol seleccionado";
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.ROLE_NOT_FOUND.toString(), role.getName() );
            }
            throw new AccessControlException( CatalogError.ROLE_NOT_FOUND );
        }
    }

    @Override
    public List < Role > getRoleMenu() throws AccessControlException {
        List < RoleMenuEntity > entities = roleMenuRepository.findAll();
        Type listType = new TypeToken < List < Role > >() {
        }.getType();
        if ( !entities.isEmpty() ) {
            List < RoleMenuEntity > roleEntities = verifyRoleList2( entities );
            return mapper.map( roleEntities, listType );
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.MENU_ROLE_LIST_EMPTY.toString() );
            }
            throw new AccessControlException( CatalogError.MENU_ROLE_LIST_EMPTY );
        }
    }

    @Override
    public String deleteRole( Role role ) throws AccessControlException {
        RoleEntity entity = mapper.map( role, RoleEntity.class );
        try {
            roleRepository.delete( entity );
            return "Rol eliminado correctamente";
        } catch ( EntityNotFoundException e ) {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.ROLE_NOT_FOUND_2.toString(), role.getName() );
            }
            throw new AccessControlException( CatalogError.ROLE_NOT_FOUND_2 );
        } catch ( DataIntegrityViolationException e ) {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.ROLE_INTEGRITY.toString(), role.getName() );
            }
            throw new AccessControlException( CatalogError.ROLE_INTEGRITY );
        }
    }

    @Override
    public String deletePrivilegie( Privilege privilege ) throws AccessControlException {
        PrivilegeEntity entity = mapper.map( privilege, PrivilegeEntity.class );
        try {
            privilegeRepository.delete( entity );
            return "Privilegio eliminado correctamente";
        } catch ( EntityNotFoundException e ) {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.PRIVILEGE_NOT_FOUND.toString(), privilege.getName() );
            }
            throw new AccessControlException( CatalogError.PRIVILEGE_NOT_FOUND );
        } catch ( DataIntegrityViolationException e ) {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.PRIVILEGE_INTEGRITY.toString(), privilege.getName() );
            }
            throw new AccessControlException( CatalogError.PRIVILEGE_INTEGRITY );
        }
    }

    @Override
    public String createRole( Role role ) throws AccessControlException {
        try {
            RoleEntity roleEntity = mapper.map( role, RoleEntity.class );
            roleEntity.setName( roleEntity.getName().toUpperCase() );
            baseService.create( roleRepository, role.getName(), roleEntity );
            return "Rol creado correctamente";
        } catch ( DataBaseException e ) {
            throw new AccessControlException( e.getError() );
        }
    }

    @Override
    public String updateRole( Role role ) throws AccessControlException {
        try {
            RoleEntity roleEntity = mapper.map( role, RoleEntity.class );
            baseService.modify( roleRepository, role.getName(), roleEntity );
            return "Rol modificado correctamente";
        } catch ( DataBaseException e ) {
            throw new AccessControlException( e.getError() );
        }
    }

    @Override
    public String createPrivilege( Privilege privilege ) throws AccessControlException {
        try {
            PrivilegeEntity privilegeEntity = mapper.map( privilege, PrivilegeEntity.class );
            baseService.create( privilegeRepository, privilege.getName(), privilegeEntity );
            return "Privilegio creado correctamente";
        } catch ( DataBaseException e ) {
            throw new AccessControlException( e.getError() );
        }
    }

    @Override
    public String createPrivilege( List < Privilege > privileges ) {
        Type type = new TypeToken < List < PrivilegeEntity > >() {
        }.getType();
        List < PrivilegeEntity > entities = mapper.map( privileges, type );
        privilegeRepository.saveAll( entities );
        return "Privilegios creados correctamente";
    }

    @Override
    public List < LogEvent > viewLogs( String userName ) throws AccessControlException {
        Type listType = new TypeToken < List < LogEvent > >() {
        }.getType();
        List < LogEventEntity > entities = logEventRepository.findByUserNameOrderByTimeDesc( userName );
        if ( entities.isEmpty() ) {
            throw new AccessControlException( CatalogError.LOG_NOT_FOUND );
        }
        return mapper.map( entities, listType );

    }

    @Override
    public List < LogEvent > viewLogs( String form, String to ) throws AccessControlException {
        Type listType = new TypeToken < List < LogEvent > >() {
        }.getType();
        try {
            List < LogEventEntity > entities = logEventRepository.findByTimeBetweenOrderByTimeDesc( new SimpleDateFormat( PATTERN ).parse( form ), new SimpleDateFormat( PATTERN ).parse( to ) );
            if ( entities.isEmpty() ) {
                throw new AccessControlException( CatalogError.LOG_NOT_FOUND );
            }
            return mapper.map( entities, listType );
        } catch ( ParseException e ) {
            throw new AccessControlException( CatalogError.DATE_ERROR );
        }
    }

    @Override
    public List < LogEvent > viewLogs( String form, String to, String name ) throws AccessControlException {
        Type listType = new TypeToken < List < LogEvent > >() {
        }.getType();
        try {
            List < LogEventEntity > entities = logEventRepository.findByTimeBetweenAndUserNameOrderByTimeDesc( new SimpleDateFormat( PATTERN ).parse( form ), new SimpleDateFormat( PATTERN ).parse( to ), name );
            if ( entities.isEmpty() ) {
                throw new AccessControlException( CatalogError.LOG_NOT_FOUND );
            }
            return mapper.map( entities, listType );
        } catch ( ParseException e ) {
            throw new AccessControlException( CatalogError.DATE_ERROR );
        }
    }

    private List < RoleEntity > verifyRoleList( List < RoleEntity > entities ) {
        List < RoleEntity > roleEntities = new ArrayList <>();
        for ( RoleEntity roleEntity : entities ) {
            if ( !( roleEntity.getName().equals( APP_SOCKET ) || roleEntity.getName().equals( APP_TRANSACTIONAL )
                    || roleEntity.getName().equals( ATM_BASICO ) || roleEntity.getName().equals( ATM_ADVANCE )
                    || roleEntity.getName().equals( ATM_FULL ) || roleEntity.getName().equals( ROLE_ATM ) ) ) {
                roleEntities.add( roleEntity );
            }
        }
        return roleEntities;
    }

    private List < RoleMenuEntity > verifyRoleList2( List < RoleMenuEntity > entities ) {
        List < RoleMenuEntity > roleEntities = new ArrayList <>();
        for ( RoleMenuEntity roleEntity : entities ) {
            if ( !( roleEntity.getName().equals( APP_SOCKET ) || roleEntity.getName().equals( APP_TRANSACTIONAL )
                    || roleEntity.getName().equals( ATM_BASICO ) || roleEntity.getName().equals( ATM_ADVANCE )
                    || roleEntity.getName().equals( ATM_FULL ) || roleEntity.getName().equals( ROLE_ATM ) ) ) {
                roleEntities.add( roleEntity );
            }
        }
        return roleEntities;
    }
    //Cambio release/eventos

}
