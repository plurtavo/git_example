package mx.com.prosa.nabhi.jse.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.jse.business.IATMNotifyService;
import mx.com.prosa.nabhi.jse.config.SwaggerConfig;
import mx.com.prosa.nabhi.jse.core.SupervisorAdvice;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jse.request.AtmNotificationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "ntf" )
@CrossOrigin( origins = { "*" }, maxAge = 6000 )
@Api( tags = { SwaggerConfig.TAG_1 } )
public class AtmNotificationsController {

    private final IATMNotifyService notifyService;

    private static final String[] ALLOWED_FIELDS = new String[]{ "ip", "termId", "device", "status", "extra" };

    @InitBinder( "AtmNotificationModel" )
    public void initBinder( WebDataBinder binder ) {
        binder.setAllowedFields( ALLOWED_FIELDS );
    }

    @Autowired
    public AtmNotificationsController( IATMNotifyService notifyService ) {
        this.notifyService = notifyService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping()
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Maneja los eventos de cada uno de los dispositivos del cajero", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < String > > atmNotification( @RequestBody AtmNotificationModel model ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( notifyService.sendToDevice( model ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }

    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/response" )
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Maneja las respuestas de los comandos XFS de los cajeros", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < Boolean > > atmResponse( @RequestBody AtmNotificationModel model ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( notifyService.updateDevice( model ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/supervisor" )
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Permite almacenar los eventos realizados en un cajero usando el modo supervisor", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < String > > atmSupervisor( @RequestBody SupervisorAdvice model ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( notifyService.supervisorAdvice( model ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    //Cambio release/redcat    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/endowment" )
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Permite actualizar los contadores cuando se realiza una dotación", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < Boolean > > endowment( @RequestBody AtmNotificationModel model ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( notifyService.endowment( model ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

}
