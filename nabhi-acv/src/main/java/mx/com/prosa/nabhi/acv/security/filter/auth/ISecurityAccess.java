package mx.com.prosa.nabhi.acv.security.filter.auth;

import mx.com.prosa.nabhi.misc.exception.acv.UserAccessException;
import org.springframework.security.core.userdetails.UserDetails;

public interface ISecurityAccess {

    UserDetails findUserDetail( String name ) throws UserAccessException;
}
