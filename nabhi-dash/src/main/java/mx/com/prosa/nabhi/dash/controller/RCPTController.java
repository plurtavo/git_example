package mx.com.prosa.nabhi.dash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.IRCPTService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.ReceiptException;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jdb.RCPT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "rcpt/" )
@Api( tags = { SwaggerConfig.TAG_15 } )
public class RCPTController {

    private final IRCPTService iRCPTService;

    @Autowired
    public RCPTController( IRCPTService iRCPTService ) {
        this.iRCPTService = iRCPTService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PutMapping
    @ApiOperation( "Permite crear un RCPT en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'create-rcpt' )" )
    public ResponseEntity < ResponsePayload < String > > create( @RequestBody RCPT value ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( iRCPTService.create( value ) ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping
    @ApiOperation( "Permite modificar un RCPT en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'write-rcpt' )" )
    public ResponseEntity < ResponsePayload < String > > modify( @RequestBody RCPT value ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( iRCPTService.modify( value ) ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @DeleteMapping
    @ApiOperation( "Permite eliminar un RCPT en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'delete-rcpt' )" )
    public ResponseEntity < ResponsePayload < String > > delete( @RequestBody RCPT value ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( iRCPTService.delete( value ) ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "find/{name:.+}" )
    @ApiOperation( "Permite buscar un RCPT en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-rcpt' )" )
    public ResponseEntity < ResponsePayload < RCPT > > findRCPT( @PathVariable String name ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( iRCPTService.findById( name ) ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping
    @ApiOperation( "Permite buscar todos los RCPT en la base de datos de acuerdo al FIID del usuario" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-type' )" )
    public ResponseEntity < ResponsePayload < List < RCPT > > > findAll() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( iRCPTService.findAll() ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "names" )
    @ApiOperation( "Permite buscar todos los RCPT en la base de datos de acuerdo al FIID del usuario" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-type' )" )
    public ResponseEntity < ResponsePayload < List < String > > > findAllOnlyName() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( iRCPTService.findAllOnlyNames() ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "test/{name:.+}" )
    @ApiOperation( "Permite buscar todos los RCPT en la base de datos de acuerdo al FIID del usuario" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-type' )" )
    public ResponseEntity < ResponsePayload < String > > test( @PathVariable String name ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( iRCPTService.test( name ) ) );
        } catch ( ReceiptException | DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

}
