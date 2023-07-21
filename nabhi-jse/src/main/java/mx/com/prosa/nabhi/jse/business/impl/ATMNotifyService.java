package mx.com.prosa.nabhi.jse.business.impl;

import mx.com.prosa.nabhi.jse.business.IATMNotifyService;
import mx.com.prosa.nabhi.jse.core.ErrorDevices;
import mx.com.prosa.nabhi.jse.core.SupervisorAdvice;
import mx.com.prosa.nabhi.jse.core.database.JournalOperation;
import mx.com.prosa.nabhi.jse.core.database.memory.atm.ATMSearch;
import mx.com.prosa.nabhi.jse.core.esq.NotificationsManagement;
import mx.com.prosa.nabhi.jse.core.i8583.reversal.Reversal;
import mx.com.prosa.nabhi.misc.constants.XFSDevices;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.model.devices.DevicesWrapper;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Cassette;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Dispenser;
import mx.com.prosa.nabhi.misc.model.devices.constants.AtmEvent;
import mx.com.prosa.nabhi.misc.model.devices.constants.AtmResponse;
import mx.com.prosa.nabhi.misc.model.devices.constants.cdm.CDMEvent;
import mx.com.prosa.nabhi.misc.model.devices.constants.cdm.CDMResponse;
import mx.com.prosa.nabhi.misc.model.devices.constants.idc.IDCEvent;
import mx.com.prosa.nabhi.misc.model.devices.constants.idc.IDCResponse;
import mx.com.prosa.nabhi.misc.model.devices.constants.pin.PINEvent;
import mx.com.prosa.nabhi.misc.model.devices.constants.pin.PINResponse;
import mx.com.prosa.nabhi.misc.model.devices.constants.ptr.PTREvent;
import mx.com.prosa.nabhi.misc.model.devices.constants.ptr.PTRResponse;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDDevice;
import mx.com.prosa.nabhi.misc.model.jse.request.AtmNotificationModel;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ATMNotifyService implements IATMNotifyService {

    private static final Logger LOG = LoggerFactory.getLogger( ATMNotifyService.class );

    private final Reversal reversalPartial;
    private final JournalOperation journalOperation;
    private final ATMSearch atmSearch;
    private final ModelMapper modelMapper;
    private final ErrorDevices errorDevices;
    private final NotificationsManagement notificationsManagement;

    @Autowired
    public ATMNotifyService( Reversal reversalPartial, JournalOperation journalOperation, ATMSearch atmSearch, ModelMapper modelMapper, ErrorDevices errorDevices, NotificationsManagement notificationsManagement ) {
        this.reversalPartial = reversalPartial;
        this.journalOperation = journalOperation;
        this.atmSearch = atmSearch;
        this.modelMapper = modelMapper;
        this.errorDevices = errorDevices;
        this.notificationsManagement = notificationsManagement;
    }

    @Override
    public String sendToDevice( AtmNotificationModel model ) throws ATMException {
        int code = ( int ) model.getExtra().get( "msg1" );
        switch ( model.getDevice() ) {
            case "IDC":
                IDCEvent idcEvent = IDCEvent.getEventFromCode( code );
                return checkAction( model, idcEvent );
            case "PTR":
                PTREvent ptrEvent = PTREvent.getEventFromCode( code );
                return checkAction( model, ptrEvent );
            case "CDM":
                CDMEvent cdmEvent = CDMEvent.getEventFromCode( code );
                AtmResponse response = CDMResponse.getResponseByCode( code );
                cdmEvent( model, cdmEvent );
                cdmResponse( model, response );
                return checkAction( model, cdmEvent );
            case "PIN":
                PINEvent pinEvent = PINEvent.getEventFromCode( code );
                return checkAction( model, pinEvent );
            case "SIU":
            default:
                return "Mensaje Enviado";
        }
    }

    @Override
    public String supervisorAdvice( SupervisorAdvice advice ) {
        StringBuilder builder = new StringBuilder();
        for ( String s : advice.getMessages() ) {
            builder.append( s );
            builder.append( "\n" );
        }
        journalOperation.write( advice.getTermId(), builder.toString() );
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "ATM %s Mensaje %s", advice.getTermId(), builder ) );
        }
        return "Mensaje guardado en Journal";
    }

    //Angel Serralde López 26/06/2021 - REDCAT
    @Override
    public boolean endowment( AtmNotificationModel model ) throws ATMException {
        List < Cassette > cassettesInput = new ArrayList <>();
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Realizando dotación para el cajero %s", model.getTermId() ) );
        }
        Cassette cashUnitCounters = modelMapper.map( model.getExtra().get( "msg3" ), Cassette.class );
        cassettesInput.add( cashUnitCounters );
        findAndUpdateLogicalCashUnits( cassettesInput, model.getTermId() );
        return false;
    }

    private void cdmEvent( AtmNotificationModel model, AtmEvent event ) throws ATMException {
        if ( event.getActions().charAt( 3 ) == '1' ) {
            action( model );
        }
    }

    private void cdmResponse( AtmNotificationModel model, AtmResponse event ) throws ATMException {
        if ( event.getSeverity().equals( "-1" ) || event.getSeverity().equals( "-2" ) ) {
            if ( LOG.isInfoEnabled() ) {
                LOG.info( String.format( "%s: %s", model.getTermId(), event.getMessage() ) );
            }
            action( model );
        }
    }

    private void action( AtmNotificationModel model ) throws ATMException {
        final String code = model.getExtra().get( "msg1" ).toString();
        List < Cassette > cassettesInput = new ArrayList <>();
        if ( code.equals( "300" ) || code.contains( "-" ) ) {
            List < ? > maps = modelMapper.map( model.getExtra().get( "msg3" ), List.class );
            for ( Object cassettes : maps ) {
                cassettesInput.add( modelMapper.map( cassettes, Cassette.class ) );
            }
        } else {
            Cassette cashUnitCounters = modelMapper.map( model.getExtra().get( "msg3" ), Cassette.class );
            cassettesInput.add( cashUnitCounters );
        }
        findAndUpdateCashUnits( cassettesInput, model.getTermId() );

        if ( code.contains( "-" ) ) {
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "Hubo un problema al dispensar en el cajero %s", model.getTermId() ) );
            }
            List < Cassette > cassettesBefore = new ArrayList <>();
            List < ? > maps = modelMapper.map( model.getExtra().get( "msg4" ), List.class );
            for ( Object cassettes : maps ) {
                cassettesBefore.add( modelMapper.map( cassettes, Cassette.class ) );
            }
            if ( LOG.isInfoEnabled() ) {
                LOG.debug( String.format( "Generando reverso del cajero: %s", model.getTermId() ) );
                LOG.debug( cassettesInput.toString() );
                LOG.debug( cassettesBefore.toString() );
            }
            doReversal( cassettesInput, cassettesBefore, model );
        }

    }

    private String checkAction( AtmNotificationModel model, AtmEvent event ) {
        String actions = event.getActions();
        if ( actions.charAt( 0 ) == '1' ) {
            journalOperation.write( model.getTermId(), event.getJournalMessage() );
        }
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "ATM %s Mensaje %s", model.getTermId(), event.getJournalMessage() ) );
        }
        return "Mensaje guardado en Journal";
    }

    @Override
    public boolean updateDevice( AtmNotificationModel model ) throws ATMException {
        boolean status = ( model.getStatus().equals( "ONLINE" ) );
        try {
            errorDevices.error( model.getDevice(), model.getTermId(), status );
        } catch ( ATMException e ) {
            return false;
        }
        if ( model.getExtra() == null ) {
            return false;
        }
        int code = ( int ) model.getExtra().get( "msg1" );
        AtmResponse response;
        int dev;
        switch ( XFSDevices.valueOf( model.getDevice() ) ) {
            case PTR:
                response = PTRResponse.getResponseByCode( code );
                dev = 1;
                break;
            case IDC:
                response = IDCResponse.getResponseByCode( code );
                dev = 2;
                break;
            case CDM:
                response = CDMResponse.getResponseByCode( code );
                dev = 3;
                break;
            case PIN:
                response = PINResponse.getResponseByCode( code );
                dev = 4;
                break;
            case SIU:
                dev = 8;
                response = new AtmResponse() {
                    @Override
                    public int getValue() {
                        return code;
                    }

                    @Override
                    public String getSeverity() {
                        return null;
                    }

                    @Override
                    public String getMessage() {
                        return null;
                    }
                };
                break;
            default:
                throw new ATMException( CatalogError.XFS_NOT_FOUND );
        }
        if ( ( ( 100 * dev ) / response.getValue() ) == 1 ) {
            return true;
        }
        notificationsManagement.sendEvent( model.getTermId(), response.getValue() );
        return true;
    }

    private void findAndUpdateCashUnits( List < Cassette > cassettesInput, String termID ) throws ATMException {
        //GET DEVICES
        ATDDevice atd = atmSearch.searchDevice( termID );
        // GET DEVICE WRAPPER
        DevicesWrapper deviceWrapper = atd.getTerminalDevices();
        //GET DISPENSER
        Dispenser dispenser = deviceWrapper.getDispenser();
        // GET LIST OF CASSETTES
        //Cambio para crear una lista con caseteras fisicas extrayendolas de las caseteras lógicas
        //Angel Serralde López 26/06/2021 - REDCAT
        List < Cassette > cassettesBD = dispenser.getCassettes();
        //REPLACE CASSETTES
        for ( Cassette cassetteInput : cassettesInput ) {
            for ( int j = 0; j < cassettesBD.size(); j++ ) {
                if ( cassetteInput.getCassetteIndex() == cassettesBD.get( j ).getCassetteIndex() ) {
                    //Se cambia el modelo de Cassette por LogicalCassette
                    //Para no sobreescribir datos, se obtiene la data de la base de datos y se setea solo la Cassette
                    //Angel Serralde López 26/06/2021 - REDCAT
                    cassettesBD.get( j ).setPhysical( cassetteInput.getPhysical() );
                    cassettesBD.get( j ).getLogical().setTotalDispensed( cassetteInput.getPhysical().getDispensed() + cassettesBD.get( j ).getLogical().getTotalDispensed() );
                    cassettesBD.get( j ).setDenomination( cassetteInput.getDenomination() );
                    cassettesBD.get( j ).setType( cassetteInput.getType() );
                    cassettesBD.get( j ).setCurrency( cassetteInput.getCurrency() );
                }
            }
        }
        dispenser.setCassettes( cassettesBD );
        deviceWrapper.setDispenser( dispenser );
        atmSearch.saveDevice( atd );
    }

    private int compareCounters( List < Cassette > before, List < Cassette > after ) {
        List < Cassette > cassettes = new ArrayList <>();
        for ( Cassette cassetteInput : after ) {
            for ( Cassette value : before ) {
                if ( cassetteInput.getCassetteIndex() == value.getCassetteIndex() ) {
                    Cassette cassette = new Cassette();
                    cassette.setDenomination( cassetteInput.getDenomination() );
                    cassette.getPhysical().setPresented( cassetteInput.getPhysical().getPresented() - value.getPhysical().getPresented() );
                    cassettes.add( cassette );
                }
            }
        }
        int total = 0;
        for ( Cassette cassette : cassettes ) {
            total += cassette.getDenomination() * cassette.getPhysical().getPresented();
        }
        return total;
    }

    private void doReversal( List < Cassette > input, List < Cassette > bd, AtmNotificationModel model ) {
        try {
            reversalPartial.makeReversal( model, compareCounters( input, bd ) );
            model.getExtra().remove( "msg3" );
            model.getExtra().remove( "msg4" );
            journalOperation.write( model.getTermId(), model.getExtra().get( "msg2" ).toString() );
        } catch ( ATMException e ) {
            LOG.error( e.getMessage() );
        }

    }

    //Angel Serralde López 26/06/2021 - REDCAT
    private void findAndUpdateLogicalCashUnits( List < Cassette > cassettesInput, String termID ) throws ATMException {
        //GET DEVICES
        ATDDevice atd = atmSearch.searchDevice( termID );
        // GET DEVICE WRAPPER
        DevicesWrapper deviceWrapper = atd.getTerminalDevices();
        //GET DISPENSER
        Dispenser dispenser = deviceWrapper.getDispenser();
        // GET LIST OF CASSETTES
        List < Cassette > cassettesBD = dispenser.getCassettes();
        //REPLACE CASSETTES
        for ( Cassette cassetteInput : cassettesInput ) {
            for ( int j = 0; j < cassettesBD.size(); j++ ) {
                if ( cassetteInput.getCassetteIndex() == cassettesBD.get( j ).getCassetteIndex() ) {
                    //Se cambia el modelo de Cassette
                    //Para no sobreescribir datos, se obtiene la data de la base de datos y se setea solo la Cassette
                    //Primero se obtiene la casetera, luego se obtienen sus datos como, incremente y logicalCurrent para conocer
                    //cuanto se doto y cuanto tiene logicamente
                    cassetteInput.getLogical().setIncrement( cassetteInput.getLogical().getIncrement() + cassettesBD.get( j ).getLogical().getIncrement() );
                    cassetteInput.getLogical().setTotalDispensed( cassettesBD.get( j ).getLogical().getTotalDispensed() );
                    cassettesBD.set( j, cassetteInput );
                }
            }
        }
        dispenser.setCassettes( cassettesBD );
        deviceWrapper.setDispenser( dispenser );
        atmSearch.saveDevice( atd );
    }
}


