package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.dash.security.ldap.LDAPException;
import mx.com.prosa.nabhi.misc.exception.sanitize.SanitazeException;
import mx.com.prosa.nabhi.misc.exception.user.AccessControlException;
import mx.com.prosa.nabhi.misc.model.jdb.*;

import java.util.List;

public interface IUserService {

    String create( User tokenUser, boolean isAtmOrService ) throws AccessControlException, SanitazeException, LDAPException;

    String modify( User tokenUser, boolean isAtmOrService ) throws AccessControlException, SanitazeException, LDAPException;

    String delete( User tokenUser, boolean isAtmOrService ) throws AccessControlException, LDAPException;

    String enable( User tokenUser ) throws AccessControlException;

    String disable( User tokenUser ) throws AccessControlException;

    List < Role > getRoles() throws AccessControlException;

    List < User > findAll() throws AccessControlException;

    List < String > findAllNames() throws AccessControlException;

    User findById( String id ) throws AccessControlException;

    //Cambio release/eventos
    List < Privilege > getPrivileges() throws AccessControlException;

    String addMenus( List < MenuObject > menus ) throws AccessControlException;

    String deleteMenu( MenuObject menu ) throws AccessControlException;

    List < MenuObject > getMenus() throws AccessControlException;

    String updateRoleMenu( Role role ) throws AccessControlException;

    List < Role > getRoleMenu() throws AccessControlException;

    String deleteRole( Role role ) throws AccessControlException;

    String deletePrivilegie( Privilege privilege ) throws AccessControlException;

    String createRole( Role role ) throws AccessControlException;

    String updateRole( Role role ) throws AccessControlException;

    String createPrivilege( Privilege privilege ) throws AccessControlException;

    String createPrivilege( List < Privilege > privileges ) throws AccessControlException;

    List < LogEvent > viewLogs( String userName ) throws AccessControlException;

    List < LogEvent > viewLogs( String form, String to ) throws AccessControlException;

    List < LogEvent > viewLogs( String form, String to, String name ) throws AccessControlException;

    //Cambio release/eventos
}
