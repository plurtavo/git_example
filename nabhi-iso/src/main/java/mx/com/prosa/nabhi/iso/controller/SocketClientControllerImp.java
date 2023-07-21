package mx.com.prosa.nabhi.iso.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.iso.business.ISocketServices;
import mx.com.prosa.nabhi.iso.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "client" )
@CrossOrigin( origins = { "*" }, maxAge = 6000 )
@Api( tags = { SwaggerConfig.TAG_2 } )
public class SocketClientControllerImp {

    private final ISocketServices socketServices;

    @Autowired
    public SocketClientControllerImp( ISocketServices socketServices ) {
        this.socketServices = socketServices;
    }
    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/logon/{nodeName:.+}" )
    @PreAuthorize( "hasAnyAuthority( 'socket' )" )
    @ApiOperation( "Generates an ISO-8583 1987 Type 0800 message S70 001 and routes it to the specified HISO Base 24 Interface." )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < String > > logon( @PathVariable String nodeName ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( socketServices.logon( nodeName ) ) );
        } catch ( ISO8583Exception | SocketException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/logoff/{nodeName:.+}" )
    @PreAuthorize( "hasAnyAuthority( 'socket' )" )
    @ApiOperation( "Generates an ISO-8583 1987 Type 0800 message S70 002 and routes it to the specified HISO Base 24 Interface." )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < String > > logoff( @PathVariable String nodeName ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( socketServices.logoff( nodeName ) ) );
        } catch ( ISO8583Exception | SocketException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/echo/{nodeName:.+}" )
    @PreAuthorize( "hasAnyAuthority( 'socket' )" )
    @ApiOperation( "Generates an ISO-8583 1987 Type 0800 S70 301 message and routes it to the specified HISO Base 24 interface. Starts a task with a configured timeout on node" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < String > > echo( @PathVariable String nodeName ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( socketServices.echo( nodeName ) ) );
        } catch ( SocketException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/delete/{nodeName:.+}" )
    @PreAuthorize( "hasAnyAuthority( 'socket' )" )
    @ApiOperation( value = "Permite eliminar un Socket TCP", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < String > > delete( @PathVariable String nodeName ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( socketServices.delete( nodeName ) ) );
        } catch ( SocketException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/create/{nodeName:.+}" )
    @PreAuthorize( "hasAnyAuthority( 'socket' )" )
    @ApiOperation( value = "Permite crear un Socket TCP", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < String > > create( @PathVariable String nodeName ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( socketServices.create( nodeName ) ) );
        } catch ( SocketException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
