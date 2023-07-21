package mx.com.prosa.nabhi.dash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.IScreenService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jdb.ScreenGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "screen/" )
@Api( tags = { SwaggerConfig.TAG_16 } )
public class ScreenController {

    private final IScreenService screenService;

    @Autowired
    public ScreenController( IScreenService screenService ) {
        this.screenService = screenService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PutMapping
    @ApiOperation( "Permite crear una Grupo de Pantallas en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'create-screen-group' )" )
    public ResponseEntity < ResponsePayload < String > > create( @RequestBody ScreenGroup value ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( screenService.create( value ) ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping
    @ApiOperation( "Permite modificar un Grupo de Pantallas en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'write-screen-group' )" )
    public ResponseEntity < ResponsePayload < String > > modify( @RequestBody ScreenGroup value ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( screenService.modify( value ) ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @DeleteMapping
    @ApiOperation( "Permite eliminar un Grupo de Pantallas en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'delete-screen-group' )" )
    public ResponseEntity < ResponsePayload < String > > delete( @RequestBody ScreenGroup value ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( screenService.delete( value ) ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "find/{groupId:.+}" )
    @ApiOperation( "Permite buscar un Grupo de Pantallas en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-screen' )" )
    public ResponseEntity < ResponsePayload < ScreenGroup > > findBIN( @PathVariable String groupId ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( screenService.findById( groupId ) ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping
    @ApiOperation( "Permite buscar todos los Grupos de Pantallas en la base de datos de acuerdo al FIID del usuario" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-type' )" )
    public ResponseEntity < ResponsePayload < List < ScreenGroup > > > findAll() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( screenService.findAll() ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "names" )
    @ApiOperation( "Permite buscar todos los Grupos de Pantallas en la base de datos de acuerdo al FIID del usuario" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-type' )" )
    public ResponseEntity < ResponsePayload < List < String > > > findAllOnlyName() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( screenService.findAllOnlyNames() ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "names/{fiid:.+}" )
    @ApiOperation( "Permite buscar todos los Grupos de Pantallas en la base de datos de acuerdo al FIID del usuario" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-type' )" )
    public ResponseEntity < ResponsePayload < List < String > > > findAllOnlyName( @PathVariable String fiid ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( screenService.findAllOnlyNames( fiid ) ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/update/{group:.+}/{screen:.+}" )
    @ApiOperation( value = "Permite actualizar el algoritmo de dispensado a un grupo de cajeros", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'update-algorithm' )" )
    public ResponseEntity < ResponsePayload < String > > updateAlgorithmByGroup( @PathVariable String group, @PathVariable String screen ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( screenService.updateGroup( group, screen ) ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
