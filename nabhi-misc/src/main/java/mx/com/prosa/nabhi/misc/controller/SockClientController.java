package mx.com.prosa.nabhi.misc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.sockets.TCPServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "client" )
//Cambio release/abc
@Api( tags = { "Operación de Sockets TCP" } )
//Cambio release/abc
public class SockClientController {

    //Cambio release/abc
    private final ISockClientServices clientServices;

    @Autowired
    public SockClientController( ISockClientServices clientServices ) {
        this.clientServices = clientServices;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/onSocket/{nodeName:.+}" )
    @PreAuthorize( "hasAnyAuthority( 'socket' )" )
    @ApiOperation( value = "Permite iniciar un Socket TCP", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < String > > onSocket( @PathVariable String nodeName ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( clientServices.onSocket( nodeName ) ) );
        } catch ( SocketException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/offSocket/{nodeName:.+}" )
    @PreAuthorize( "hasAnyAuthority( 'socket' )" )
    @ApiOperation( value = "Permite apagar un Socket TCP", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < String > > offSocket( @PathVariable String nodeName ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( clientServices.offSocket( nodeName ) ) );
        } catch ( SocketException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/getAllSocket" )
    @PreAuthorize( "hasAnyAuthority( 'socket' )" )
    @ApiOperation( value = "Permite recuperar una lista de configuración Sockets TCP", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < List < TCPServiceInfo > > > getAllSocket() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( clientServices.getAllSocket() ) );
        } catch ( SocketException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/getSocket/{nodeName:.+}" )
    @PreAuthorize( "hasAnyAuthority( 'socket' )" )
    @ApiOperation( value = "Permite recuperar una configuración Socket TCP", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < TCPServiceInfo > > getSingleSocket( @PathVariable String nodeName ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( clientServices.getSingleSocket( nodeName ) ) );
        } catch ( SocketException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
    //Cambio release/abc

}
