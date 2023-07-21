package mx.com.prosa.nabhi.dash.controller;
//Cambio release/redcat

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.ISockService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "socket" )
@Api( tags = { SwaggerConfig.TAG_7 } )
public class SocketController {

    private final ISockService sockService;

    @Autowired
    public SocketController( ISockService sockService ) {
        this.sockService = sockService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/{app:.+}/{op:.+}/{nodeName:.+}" )
    @ApiOperation( "Permite realizar operaciones de los Nodos TCP de los servicios ISO y JKE" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'socket' )" )
    public ResponseEntity < ResponsePayload < String > > findAllState( @PathVariable String app, @PathVariable String op, @PathVariable String nodeName ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( sockService.sock( app, op, nodeName ) ) );
        } catch ( SocketException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}

