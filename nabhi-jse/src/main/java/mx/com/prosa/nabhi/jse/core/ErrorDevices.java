package mx.com.prosa.nabhi.jse.core;

import mx.com.prosa.nabhi.jse.core.database.memory.atm.ATMSearch;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorDevices {

    private final ATMSearch atmSearch;
    private static final Logger LOG = LoggerFactory.getLogger( ErrorDevices.class );

    @Autowired
    public ErrorDevices( ATMSearch atmSearch ) {
        this.atmSearch = atmSearch;
    }

    public void error( String device, String atm, boolean status ) throws ATMException {
        ATDDevice atd = atmSearch.searchDevice( atm );
        switch ( device ) {
            case "CDM":
                if ( !validateCDM( atd, status ) ) {
                    return;
                }
                updateCDM( atd, status );
                break;
            case "PTR":
                if ( !validatePTR( atd, status ) ) {
                    return;
                }
                updatePTR( atd, status );
                break;
            case "IDC":
                if ( !validateIDC( atd, status ) ) {
                    return;
                }
                updateIDC( atd, status );
                break;
            case "PIN":
                if ( !validatePIN( atd, status ) ) {
                    return;
                }
                updatePIN( atd, status );
                break;
            case "SIU":
                if ( !validateSIU( atd, status ) ) {
                    return;
                }
                updateSIU( atd, status );
                break;
            default:
                if ( LOG.isErrorEnabled() ) {
                    LOG.error( "Invalid device error" );
                }
                break;
        }
        atmSearch.saveDevice( atd );
    }

    private boolean validateCDM( ATDDevice atd, boolean status ) {
        return ( !status || atd.getTerminalDevices().getDispenser().getStatus() != 1 ) &&
                ( status || atd.getTerminalDevices().getDispenser().getStatus() != 0 );
    }

    private boolean validatePTR( ATDDevice atd, boolean status ) {
        return ( !status || atd.getTerminalDevices().getPrinter().getStatus() != 1 ) &&
                ( status || atd.getTerminalDevices().getPrinter().getStatus() != 0 );
    }

    private boolean validateIDC( ATDDevice atd, boolean status ) {
        return ( !status || atd.getTerminalDevices().getCardReader().getStatus() != 1 ) &&
                ( status || atd.getTerminalDevices().getCardReader().getStatus() != 0 );
    }

    private boolean validatePIN( ATDDevice atd, boolean status ) {
        return ( !status || atd.getTerminalDevices().getPinPad().getStatus() != 1 ) &&
                ( status || atd.getTerminalDevices().getPinPad().getStatus() != 0 );
    }

    private boolean validateSIU( ATDDevice atd, boolean status ) {
        return ( !status || atd.getTerminalDevices().getSiu().getStatus() != 1 ) &&
                ( status || atd.getTerminalDevices().getSiu().getStatus() != 0 );
    }

    private void updateCDM( ATDDevice atd, boolean status ) {
        if ( status ) {
            atd.getTerminalDevices().getDispenser().setStatus( 1 );
        } else {
            atd.getTerminalDevices().getDispenser().setStatus( 0 );
        }
    }

    private void updatePTR( ATDDevice atd, boolean status ) {
        if ( status ) {
            atd.getTerminalDevices().getPrinter().setStatus( 1 );
        } else {
            atd.getTerminalDevices().getPrinter().setStatus( 0 );
        }
    }

    private void updateIDC( ATDDevice atd, boolean status ) {
        if ( status ) {
            atd.getTerminalDevices().getCardReader().setStatus( 1 );
        } else {
            atd.getTerminalDevices().getCardReader().setStatus( 0 );
        }
    }

    private void updatePIN( ATDDevice atd, boolean status ) {
        if ( status ) {
            atd.getTerminalDevices().getPinPad().setStatus( 1 );
        } else {
            atd.getTerminalDevices().getPinPad().setStatus( 0 );
        }
    }

    private void updateSIU( ATDDevice atd, boolean status ) {
        if ( status ) {
            atd.getTerminalDevices().getSiu().setStatus( 1 );
        } else {
            atd.getTerminalDevices().getSiu().setStatus( 0 );
        }
    }

}
