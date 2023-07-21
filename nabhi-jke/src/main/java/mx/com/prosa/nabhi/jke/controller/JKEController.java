package mx.com.prosa.nabhi.jke.controller;
//Cambio release/abc

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.jke.business.IExchangeService;
import mx.com.prosa.nabhi.jke.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.keys.KeyExchangeException;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jke.KeyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@Api( tags = { SwaggerConfig.TAG_1 } )
public class JKEController {

    private final IExchangeService exchangeService;

    @Autowired
    public JKEController( IExchangeService exchangeService ) {
        this.exchangeService = exchangeService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/exchange/{nodeName:.+}" )
    @PreAuthorize( "hasAnyAuthority( 'transactional' )" )
    @ApiOperation( value = "Generates a Type 1A H25 message and routes it to the specified HSM Base 24 interface", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < String > > sendMessageTPK( @PathVariable String nodeName, @RequestBody KeyRequest message ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( exchangeService.exchange( message, nodeName ) ) );
        } catch ( SocketException | KeyExchangeException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/traslate/{nodeName:.+}" )
    @PreAuthorize( "hasAnyAuthority( 'transactional' )" )
    @ApiOperation( value = "Generates a Type 1B H25 message and routes it to the specified HSM Base 24 interface.", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < String > > sendMessagePinBlock( @PathVariable String nodeName, @RequestBody KeyRequest message ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( exchangeService.translate( message, nodeName ) ) );
        } catch ( SocketException | KeyExchangeException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
//Cambio release/abc
}
