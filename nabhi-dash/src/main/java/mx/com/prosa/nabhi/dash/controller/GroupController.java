package mx.com.prosa.nabhi.dash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.IGroupService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.dash.model.group.CriteriaSearch;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.domain.group.dto.*;
import mx.com.prosa.nabhi.misc.exception.dash.GroupException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "group" )
@Api( tags = { SwaggerConfig.TAG_3 } )
public class GroupController {

    private final IGroupService groupService;

    @Autowired
    public GroupController( IGroupService groupService ) {
        this.groupService = groupService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping
    @ApiOperation( value = "Permite crear un grupo de cajeros automáticos", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'create-group' )" )
    public ResponseEntity < ResponsePayload < String > > save( @RequestBody Group group ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( groupService.create( group ) ) );
        } catch ( GroupException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PutMapping
    @ApiOperation( value = "Permite actualizar un grupo existente de cajeros automáticos", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'update-group' )" )
    public ResponseEntity < ResponsePayload < String > > update( @RequestBody Group group ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( groupService.update( group ) ) );
        } catch ( GroupException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @DeleteMapping
    @ApiOperation( value = "Permite eliminar un grupo existente de cajeros automáticos", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'delete-group' )" )
    public ResponseEntity < ResponsePayload < String > > delete( @RequestBody Group group ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( groupService.delete( group ) ) );
        } catch ( GroupException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/find/{name:.+}" )
    @ApiOperation( value = "Permite buscar un grupo de cajeros por nombre", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'search-group' )" )
    public ResponseEntity < ResponsePayload < Group > > findByName( @PathVariable String name ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( groupService.findByName( name ) ) );
        } catch ( GroupException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping
    @ApiOperation( value = "Permite todos los grupos del banco o la red Prosa", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'search-group' )" )
    public ResponseEntity < ResponsePayload < List < Group > > > findByAll() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( groupService.findAll() ) );
        } catch ( GroupException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/names" )
    @ApiOperation( value = "Permite todos los grupos (solo los nombres) del banco o la red Prosa", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'search-group' )" )
    public ResponseEntity < ResponsePayload < List < String > > > findAllOnlyNames() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( groupService.findAllOnlyNames() ) );
        } catch ( GroupException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/idf" )
    @ApiOperation( value = "Permite obtener una lista de las FIIDs dadas de alta en el sistema", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'search-group' )" )
    public ResponseEntity < ResponsePayload < List < IDF > > > findIDFS() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( groupService.findIDFS() ) );
        } catch ( GroupException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/state" )
    @ApiOperation( value = "Permite obtener una lista de los estados dados de alta en el sistema", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'search-group' )" )
    public ResponseEntity < ResponsePayload < List < State > > > findStates() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( groupService.findStates() ) );
        } catch ( GroupException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/counties" )
    @ApiOperation( value = "Permite obtener una lista de los municipios pertenecientes a un estado en el sistema", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'search-group' )" )
    public ResponseEntity < ResponsePayload < List < County > > > findCounties( @RequestBody State state ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( groupService.findCountiesByState( state ) ) );
        } catch ( GroupException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/criteria" )
    @ApiOperation( value = "Permite realizar una búsqueda de acuerdo a los criterios especificados", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'search-group' )" )
    public ResponseEntity < ResponsePayload < List < ATD > > > getCriteria( @RequestBody CriteriaSearch criteriaSearch ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( groupService.findByCriteria( criteriaSearch ) ) );
        } catch ( GroupException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

}
