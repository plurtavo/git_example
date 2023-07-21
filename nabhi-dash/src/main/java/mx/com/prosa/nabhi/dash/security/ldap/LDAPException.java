//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
package mx.com.prosa.nabhi.dash.security.ldap;

import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class LDAPException extends GeneralException {

    public LDAPException( ErrorCode error ) {
        super( error );
    }

    public LDAPException( ErrorCode error, String additional ) {
        super( error, additional );
    }
}
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End