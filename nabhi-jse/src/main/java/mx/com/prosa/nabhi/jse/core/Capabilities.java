package mx.com.prosa.nabhi.jse.core;

import mx.com.prosa.nabhi.jse.core.database.memory.atm.ATMSearch;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.model.jdb.ScreenGroup;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.gonet.nabhi.misc.model.jdb.blob.ButtonMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Capabilities {

    private final ATMSearch atmSearch;
    private static final Logger LOG = LoggerFactory.getLogger( Capabilities.class );
    private static final List < Section > PTR = new ArrayList <>();
    private static final List < Section > CDM = new ArrayList <>();

    static {
        Collections.addAll( PTR, new Section( "SELECTOPERATION", 3 ) );
        Collections.addAll( CDM, new Section( "SELECTOPERATION", 1 ) );
    }

    @Autowired
    public Capabilities( ATMSearch atmSearch ) {
        this.atmSearch = atmSearch;
    }

    public ScreenGroup updateCapabilities( ScreenGroup screenGroup, String atm ) throws ATMException {
        String[] devices = { "CDM", "IDC", "PTR", "SIU", "PTR" };
        ATDDevice atd = atmSearch.searchDevice( atm );
        boolean status;
        for ( String device : devices ) {
            switch ( device ) {
                case "CDM":
                    status = atd.getTerminalDevices().getDispenser().getStatus() == 1;
                    modifyCapabilities( CDM, status, screenGroup );
                break;
                case "PTR":
                    status = atd.getTerminalDevices().getPrinter().getStatus() == 1;
                    modifyCapabilities( PTR, status, screenGroup );
                break;
                case "IDC":
                case "PIN":
                case "SIU":
                    break;
                default:
                    if ( LOG.isErrorEnabled() ) {
                        LOG.error( "Dispositivo de cajero de no encontrado" );
                    }
                    break;
            }
        }
        return screenGroup;

    }


    private void modifyCapabilities( List < Section > sections, boolean status, ScreenGroup screenGroup ) throws ATMException {
        for ( Section section : sections ) {
            ButtonMapping buttonMapping = findSection( section.getScreenComponent(), screenGroup );
            char[] bits = buttonMapping.getBitmap().toCharArray();
            if ( status && bits[ section.getBit() ] == '1' ) {
                bits[ section.getBit() ] = '1';
            } else {
                bits[ section.getBit() ] = '0';
            }
            StringBuilder builder = new StringBuilder();
            for ( char b : bits ) {
                builder.append( b );
            }
            buttonMapping.setBitmap( builder.toString() );
            updateSection( buttonMapping, screenGroup );
        }
    }

    private ButtonMapping findSection( String section, ScreenGroup screenGroup ) throws ATMException {
        List < ButtonMapping > buttonMappings = screenGroup.getButtonsAllowed().getButtons();
        for ( ButtonMapping buttonMapping : buttonMappings ) {
            if ( buttonMapping.getScreenComponent().equals( section ) ) {
                return buttonMapping;
            }
        }
        throw new ATMException( CatalogError.BUTTON_SECTION );
    }

    private void updateSection( ButtonMapping bitmap, ScreenGroup screenGroup ) {
        for ( ButtonMapping buttonMapping : screenGroup.getButtonsAllowed().getButtons() ) {
            if ( buttonMapping.getScreenComponent().equals( bitmap.getScreenComponent() ) ) {
                buttonMapping.setBitmap( bitmap.getBitmap() );
            }
        }
    }

    static class Section {

        private final String screenComponent;
        private final int bit;

        Section( String screenComponent, int bit ) {
            this.screenComponent = screenComponent;
            this.bit = bit;
        }

        String getScreenComponent() {
            return screenComponent;
        }

        int getBit() {
            return bit;
        }
    }
}
