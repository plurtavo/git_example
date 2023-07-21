package mx.com.prosa.nabhi.acv.business.imp;

import com.dsapi.core.sockets.crypto.Crypto;
import com.dsapi.core.sockets.crypto.CryptoException;
import mx.com.prosa.nabhi.acv.business.IOrderService;
import mx.com.prosa.nabhi.acv.model.KeyLoad;
import mx.com.prosa.nabhi.acv.model.PrintScreenRequest;
import mx.com.prosa.nabhi.acv.model.ResetCommandRequest;
import mx.com.prosa.nabhi.acv.model.ResetRequest;
import mx.com.prosa.nabhi.misc.constants.XFSDevices;
import mx.com.prosa.nabhi.misc.domain.acv.entity.ATDForAuthenticationEntity;
import mx.com.prosa.nabhi.misc.domain.acv.entity.PayloadEntity;
import mx.com.prosa.nabhi.misc.domain.acv.repository.ATDForAuthenticationRepository;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ATDForAuthenticationRepository atdRepository;
    private final Crypto crypto;
    private static final String TOPIC = "/topic/";

    @Autowired
    public OrderService( SimpMessagingTemplate simpMessagingTemplate, ATDForAuthenticationRepository atdRepository, Crypto crypto ) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.atdRepository = atdRepository;
        this.crypto = crypto;
    }

    @Override
    public String captureImage( String atm, String filename ) throws ATMException {
        validateAtmRequested( atm );
        PrintScreenRequest request = new PrintScreenRequest();
        request.setTerminalId( atm );
        request.setFileName( filename );
        request.setCreate( true );
        simpMessagingTemplate.convertAndSend( TOPIC + atm + "/print", request );
        return "Orden de captura enviada, por favor espere a que se suba al sistema";
    }

    @Override
    public String getImage( String atm, String filename ) throws ATMException {
        validateAtmRequested2( atm );
        PrintScreenRequest request = new PrintScreenRequest();
        request.setTerminalId( atm );
        request.setFileName( filename );
        request.setCreate( false );
        simpMessagingTemplate.convertAndSend( TOPIC + atm + "/print", request );
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path( "/file/" )
                .path( "download/" )
                .path( atm + "/" )
                .path( filename + ".png" )
                .toUriString();
    }

    @Override
    public String sendReset( String atm, String device ) throws ATMException {
        validateAtmRequested( atm );
        ResetRequest reset = new ResetRequest();
        reset.setDevice( XFSDevices.valueOf( device ).name() );
        simpMessagingTemplate.convertAndSend( TOPIC + atm + "/reset", reset );
        return "Reset enviado exitosamente";
    }

    @Override
    public String sendCommand( String atm, int command ) throws ATMException {
        validateAtmRequested( atm );
        ResetCommandRequest reset = new ResetCommandRequest();
        reset.setCommand( command );
        simpMessagingTemplate.convertAndSend( TOPIC + atm + "/command-atm", reset );
        return "Se envío el comando al cajero";
    }

    @Override
    public String commandUpdateScreen( String payload ) {
        PayloadEntity payloadEntity = new PayloadEntity();
        payloadEntity.setGroupId( payload );
        List< ATDForAuthenticationEntity > entities = atdRepository.findAllByScreenGroup( payloadEntity );
        if ( entities.isEmpty() ){
            return CatalogError.PAYLOAD_UPDATE_ERROR.getMessage();
        }else {
            for ( ATDForAuthenticationEntity entity : entities ){
                if ( entity.isOnline() ){
                    ResetCommandRequest update = new ResetCommandRequest();
                    update.setCommand( 7 );
                    simpMessagingTemplate.convertAndSend( TOPIC + entity.getTerminalId() + "/command-atm", update );
                }
            }
        }
        return "Se envío comando a los cajeros online con este payload";
    }

    @Override
    public String sendKey( String atm, String key, int part ) throws ATMException, CryptoException {
        validateAtmRequested( atm );
        String kcv = crypto.kcv( key.getBytes() );
        KeyLoad keyLoad = new KeyLoad();
        keyLoad.setKey( key );
        keyLoad.setPart( part );
        simpMessagingTemplate.convertAndSend( TOPIC + atm + "/keyload", keyLoad );
        return "KCV: " + kcv;
    }

    @Override
    public String sendKeyFinal( String atm, String key, int part ) throws ATMException, CryptoException {
        String kcv = sendKey( atm, key, part );
        sendKey( atm, "00000000000000000000000000000000", 3 );
        return kcv;
    }


    private void validateAtmRequested( String atm ) throws ATMException {
        Optional < ATDForAuthenticationEntity > optional = atdRepository.findById( atm );
        if ( optional.isPresent() ) {
            if ( !optional.get().isOnline() ) {
                throw new ATMException( CatalogError.ATM_NOT_ONLINE );
            }
        } else {
            throw new ATMException( CatalogError.ATM_NOT_EXIST );
        }
    }

    private void validateAtmRequested2( String atm ) throws ATMException {
        Optional < ATDForAuthenticationEntity > optional = atdRepository.findById( atm );
        if ( !optional.isPresent() ) {
            throw new ATMException( CatalogError.ATM_NOT_EXIST );
        }
    }


}
