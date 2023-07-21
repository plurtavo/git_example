package mx.com.prosa.nabhi.acv.controller;

import com.dsapi.core.sockets.crypto.CryptoException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.acv.business.IOrderService;
import mx.com.prosa.nabhi.acv.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "orders/" )
@Api( tags = { SwaggerConfig.TAG_1 } )
public class OrderController {

    private final IOrderService orderService;

    @Autowired
    public OrderController( IOrderService orderService ) {
        this.orderService = orderService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "print/make/{terminalId:.+}/{fileName:.+}" )
    @ApiOperation( value = "Permite enviar la instrucción de captura de pantalla al cajero automático", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'monitoring' )" )
    public ResponseEntity < ResponsePayload < String > > makePrint( @PathVariable String terminalId, @PathVariable String fileName ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( orderService.captureImage( terminalId, fileName ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getError().getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "print/get/{terminalId:.+}/{fileName:.+}" )
    @ApiOperation( value = "Permite enviar la instrucción de recuperar la captura de pantalla del cajero automático", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'monitoring' )" )
    public ResponseEntity < ResponsePayload < String > > getPrint( @PathVariable String terminalId, @PathVariable String fileName ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( orderService.getImage( terminalId, fileName ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getError().getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "reset/{terminalId:.+}/{device:.+}" )
    @ApiOperation( value = "Permite enviar reset a un dispositivo del cajero automático", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'monitoring' )" )
    public ResponseEntity < ResponsePayload < String > > reset( @PathVariable String terminalId, @PathVariable String device ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( orderService.sendReset( terminalId, device ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getError().getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "command-atm/{terminalId:.+}/{command:.+}" )
    @ApiOperation( value = "Permite enviar la instrucción remota a el cajero automático" +
            "\n[Command] 1 = Apaga el cajero automático" +
            "\n[Command] 2 = Reinicia el cajero automático" +
            "\n[Command] 3 = Cierra la sesión de Windows de el cajero automático" +
            "\n[Command] 4 = Aborta cualquiera de los 3 comandos anteriores", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'monitoring' )" )
    public ResponseEntity < ResponsePayload < String > > commandATM( @PathVariable String terminalId, @PathVariable int command ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( orderService.sendCommand( terminalId, command ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getError().getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }


    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "command-atm/{payload:.+}" )
    @ApiOperation( value = "Permite enviar la instrucción remota a el cajero automático para actualizar pantallas", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'write-screen-group' )" )
    public ResponseEntity < ResponsePayload < String > > commandUpdateScreen( @PathVariable String payload ) {
        return ResponseEntity.ok( ResponsePayload.of( orderService.commandUpdateScreen( payload ) ) );
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "key/{terminalId:.+}/{key:.+}/{part:.+}" )
    @ApiOperation( value = "Permite ingresar llaves a el cajero automático", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "KCV de la llave" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > sendKey( @PathVariable String terminalId, @PathVariable String key, @PathVariable int part ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( orderService.sendKey( terminalId, key, part ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getError().getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        } catch ( CryptoException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), 3 ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "key/final//{terminalId:.+}/{key:.+}/{part:.+}" )
    @ApiOperation( value = "Permite ingresar llaves a el cajero automático", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "KCV de la llave" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > sendKeyFinal( @PathVariable String terminalId, @PathVariable String key, @PathVariable int part ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( orderService.sendKeyFinal( terminalId, key, part ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getError().getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        } catch ( CryptoException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), 3 ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

}
