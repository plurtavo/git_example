//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin

package mx.com.prosa.nabhi.dash.security.ldap;
//Cambio release/redcat

import mx.com.prosa.nabhi.misc.domain.security.entity.PrivilegeEntity;
import mx.com.prosa.nabhi.misc.domain.security.entity.RoleEntity;
import mx.com.prosa.nabhi.misc.domain.security.entity.UserEntity;
import mx.com.prosa.nabhi.misc.domain.security.repository.UserRepository;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.CommunicationException;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.*;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import java.util.*;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class LDAPService {

    private static final Logger LOG = LoggerFactory.getLogger( LDAPService.class );
    private static final String OBJECT_CLASS = "objectclass";
    private static final String PERSON = "person";
    private static final String UID = "uid";
    private final LdapTemplate ldapTemplate;
    private final BaseLdapPathContextSource contextSource;
    private final UserRepository userRepository;

    @Autowired
    public LDAPService( LdapTemplate ldapTemplate, BaseLdapPathContextSource contextSource, UserRepository userRepository ) {
        this.ldapTemplate = ldapTemplate;
        this.contextSource = contextSource;
        this.userRepository = userRepository;
    }

    private LdapUser findUser( String user, String secret ) throws LDAPException {
        authenticate( user, secret );
        if ( LOG.isInfoEnabled() ) {
            LOG.info( "User Authenticate" );
        }
        return findUser( user );
    }

    public LdapUser findUser( String user ) throws LDAPException {
        LdapEntry entry = findByUid( user );
        if ( LOG.isInfoEnabled() ) {
            LOG.info( "User found" );
        }
        LdapUser ldapUser = new LdapUser( entry );
        LdapApp ldapApp = findApplication();
        ldapUser.setRoles( buildUserRoles( ldapApp, user ) );
        if ( ldapUser.getRoles().isEmpty() ) {
            LOG.error( "The user is not a member of the app" );
            throw new LDAPException( CatalogError.LDAP_ERROR_USER_NOT_MEMBER );
        }
        if ( LOG.isInfoEnabled() ) {
            LOG.info( String.format( "User member of %d roles", ldapUser.getRoles().size() ) );
            for ( String rol : ldapUser.getRoles() ) {
                LOG.info( rol );
            }
        }
        return ldapUser;
    }

    public UserDetails findUserDetail( String user ) {
        Optional < UserEntity > ou = userRepository.findById( user );
        if ( !ou.isPresent() ) {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( "User not found in database" );
            }
            throw new UsernameNotFoundException( CatalogError.LDAP_ERROR_USER_NOT_FOUND_DATA.getMessage() );
        }
        return new org.springframework.security.core.userdetails.User(
                ou.get().getId(), "not-provider", ou.get().isEnabled(), true, true,
                true, getAuthorities( ou.get().getRoles() ) );
    }

    UserDetails findUserDetail( String user, String secret ) throws LDAPException {
        LdapUser ldapUser = findUser( user, secret );
        Optional < UserEntity > ou = userRepository.findById( user );
        if ( !ou.isPresent() ) {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( user );
                LOG.error( "User not found in database" );
            }
            throw new UsernameNotFoundException( CatalogError.LDAP_ERROR_USER_NOT_FOUND_DATA.getMessage() );
        }
        return new org.springframework.security.core.userdetails.User(
                ldapUser.getIdentity(), "not-provider", ou.get().isEnabled(), true, true,
                true, getAuthorities( ou.get().getRoles() ) );
    }


    @SuppressWarnings( "unchecked" )
    private void authenticate( String user, String secret ) throws LDAPException {
        List < LdapEntryIdentification > result;
        try {
            //noinspection rawtypes
            result = ldapTemplate.search( "", "uid=" + user, SearchScope.SUBTREE.getId(), ( ContextMapper ) ( new LdapEntryIdentificationContextMapper() ) );
        } catch ( CommunicationException e ) {
            LOG.error( "Communication error with LDAP" );
            throw new LDAPException( CatalogError.LDAP_ERROR_COMMUNICATION );
        }
        if ( result.isEmpty() ) {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( "Error User not found" );
            }
            throw new LDAPException( CatalogError.LDAP_ERROR_USER_NOT_FOUND_LDAP );
        } else if ( result.size() > 1 ) {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( "Many users found" );
            }
            throw new LDAPException( CatalogError.LDAP_ERROR_USER_TO_MANY );
        } else {
            final LdapEntryIdentification entryIdentification = result.get( 0 );
            DirContext ctx = null;
            try {
                ctx = this.contextSource.getContext( entryIdentification.getAbsoluteName().toString(), secret );
                ctx.lookup( entryIdentification.getRelativeName().toString() );
            } catch ( Exception e ) {
                if ( LOG.isErrorEnabled() ) {
                    LOG.error( "Invalid credentials" );
                }
                throw new LDAPException( CatalogError.LDAP_ERROR_USER_INVALID_CREDENTIALS );
            } finally {
                LdapUtils.closeContext( ctx );
            }
        }
    }


    private LdapEntry findByUid( String uid ) throws LDAPException {
        List < LdapEntry > searchResult = buildAllUserAttributes( performQuery( query().where( OBJECT_CLASS ).is( PERSON )
                .and( UID ).is( uid ), "uid" ) );
        if ( !searchResult.isEmpty() ) {
            return searchResult.get( 0 );
        } else {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( "User not found" );
            }
            throw new LDAPException( CatalogError.LDAP_ERROR_USER_NOT_FOUND_LDAP );
        }
    }

    private LdapApp findApplication() throws LDAPException {
        final String criteria = "groupOfUniqueNames";
        List < EntryWrapper > wrappers = performQuery( query().base( "ou=CAN,ou=Aplicaciones" )
                .searchScope( SearchScope.ONELEVEL )
                .where( OBJECT_CLASS ).is( criteria ), "cn" );
        LdapApp ldapApp = new LdapApp();
        ldapApp.setName( "CAN" );
        ldapApp.setRoles( buildAppAttributes( wrappers ) );
        return ldapApp;
    }


    private List < LdapEntry > buildAllUserAttributes( List < EntryWrapper > searchResultList ) {
        List < LdapEntry > users = new ArrayList <>();
        for ( EntryWrapper wrapper : searchResultList ) {
            LdapEntry output = new LdapEntry();
            output.setIdentity( wrapper.getIdentity() );
            Map < String, Object > user = new LinkedHashMap <>();
            for ( Map.Entry < String, List < Object > > entry : wrapper.getAttributes().entrySet() ) {
                if ( !( entry.getKey().equals( "objectClass" ) ) ) {
                    String value;
                    if ( entry.getKey().equals( "userPassword" ) ) {
                        value = Arrays.toString( ( byte[] ) entry.getValue().get( 0 ) );
                    } else {
                        value = entry.getValue().get( 0 ).toString();
                    }
                    value = value.replace( "[", "" );
                    value = value.replace( "]", "" );
                    user.put( entry.getKey(), value );
                }
            }
            output.setAttributes( user );
            users.add( output );
        }
        return users;
    }

    private List < LdapEntry > buildAppAttributes( List < EntryWrapper > searchResultList ) {
        List < LdapEntry > users = new ArrayList <>();
        for ( EntryWrapper wrapper : searchResultList ) {
            LdapEntry output = new LdapEntry();
            output.setIdentity( wrapper.getIdentity() );
            Map < String, Object > user = new LinkedHashMap <>();
            for ( Map.Entry < String, List < Object > > entry : wrapper.getAttributes().entrySet() ) {
                if ( !( entry.getKey().equals( "objectClass" ) ) ) {
                    if ( entry.getKey().equals( "uniqueMember" ) ) {
                        user.put( entry.getKey(), buildUniqueMember( entry ) );
                    } else {
                        String value = entry.getValue().get( 0 ).toString();
                        value = value.replace( "[", "" );
                        value = value.replace( "]", "" );
                        user.put( entry.getKey(), value );
                    }
                }
            }
            output.setAttributes( user );
            users.add( output );
        }
        return users;
    }

    private List < EntryWrapper > performQuery( LdapQuery query, String attributeMain ) throws LDAPException {
        try {
            return ldapTemplate.search(
                    query, ( AttributesMapper < EntryWrapper > ) attributes -> buildObject( attributes, attributeMain ) );
        } catch ( NameNotFoundException e ) {
            throw new LDAPException( CatalogError.LDAP_ERROR_QUERY );
        } catch ( Exception e ) {
            throw new LDAPException( CatalogError.LDAP_ERROR_QUERY, e.getMessage() );
        }
    }

    private EntryWrapper buildObject( Attributes attributes, String attributeMain ) throws NamingException {
        Map < String, List < Object > > attrsMap = new HashMap <>();
        EntryWrapper entryWrapper = new EntryWrapper();
        NamingEnumeration < String > attrIdEnum = attributes.getIDs();
        while ( attrIdEnum.hasMoreElements() ) {
            String attrId = attrIdEnum.next();
            if ( attributeMain.equals( attrId ) ) {
                entryWrapper.setIdentity( ( String ) attributes.get( attrId ).get() );
            } else {
                Attribute attr = attributes.get( attrId );
                NamingEnumeration < ? > attrValuesEnum = attr.getAll();
                while ( attrValuesEnum.hasMore() ) {
                    attrsMap.computeIfAbsent( attrId, k -> new ArrayList <>() );
                    attrsMap.get( attrId ).add( attrValuesEnum.next() );
                }
            }
        }
        entryWrapper.setAttributes( attrsMap );
        return entryWrapper;
    }


    private List < String > buildUniqueMember( Map.Entry < String, List < Object > > entry ) {
        List < String > members = new ArrayList <>();
        for ( Object member : entry.getValue() ) {
            String str = member.toString();
            members.add( str.substring( str.indexOf( '=' ) + 1, str.indexOf( ',' ) ) );
        }
        return members;
    }

    @SuppressWarnings( "unchecked" )
    private List < String > buildUserRoles( LdapApp ldapApp, String user ) {
        List < String > roles = new ArrayList <>();
        for ( LdapEntry ldapEntry : ldapApp.getRoles() ) {
            for ( Map.Entry < String, Object > attr : ldapEntry.getAttributes().entrySet() ) {
                if ( attr.getKey().equals( "uniqueMember" ) ) {
                    for ( String s : ( List < String > ) attr.getValue() ) {
                        if ( s.equals( user ) ) {
                            roles.add( ldapEntry.getIdentity() );
                        }
                    }
                }
            }
        }
        return roles;
    }


    private Collection < ? extends GrantedAuthority > getAuthorities( final Collection < RoleEntity > roles ) {
        List < String > rol = new ArrayList <>();
        for ( RoleEntity roleEntity : roles ) {
            rol.add( roleEntity.getName() );
        }
        rol.addAll( getPrivileges( roles ) );
        return getGrantedAuthorities( rol );
    }

    private List < String > getPrivileges( final Collection < RoleEntity > roles ) {
        final List < String > privileges = new ArrayList <>();
        final List < PrivilegeEntity > collection = new ArrayList <>();
        for ( final RoleEntity role : roles ) {
            collection.addAll( role.getPrivileges() );
        }
        for ( final PrivilegeEntity item : collection ) {
            privileges.add( item.getName() );
        }

        return privileges;
    }

    private List < GrantedAuthority > getGrantedAuthorities( final List < String > privileges ) {
        final List < GrantedAuthority > authorities = new ArrayList <>();
        for ( final String privilege : privileges ) {
            authorities.add( new SimpleGrantedAuthority( privilege ) );
        }
        return authorities;
    }
}
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
