package mx.com.prosa.nabhi.dash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.IAlgorithmService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.domain.algorithm.dto.DispensedAlgorithm;
import mx.com.prosa.nabhi.misc.exception.dash.AlgorithmException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "algorithm" )
@Api( tags = { SwaggerConfig.TAG_5 } )
public class AlgorithmController {

    private final IAlgorithmService algorithmService;

    @Autowired
    public AlgorithmController( IAlgorithmService algorithmService ) {
        this.algorithmService = algorithmService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping
    @ApiOperation( value = "Permite crear un algoritmo de dispensado", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'create-algorithm' )" )
    public ResponseEntity < ResponsePayload < String > > save( @RequestBody DispensedAlgorithm algorithm ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( algorithmService.create( algorithm ) ) );
        } catch ( AlgorithmException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PutMapping
    @ApiOperation( value = "Permite actualizar un algoritmo de dispensado", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'update-algorithm' )" )
    public ResponseEntity < ResponsePayload < String > > update( @RequestBody DispensedAlgorithm algorithm ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( algorithmService.update( algorithm ) ) );
        } catch ( AlgorithmException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @DeleteMapping
    @ApiOperation( value = "Permite eliminar un algoritmo de dispensado", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'delete-algorithm' )" )
    public ResponseEntity < ResponsePayload < String > > delete( @RequestBody DispensedAlgorithm algorithm ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( algorithmService.delete( algorithm ) ) );
        } catch ( AlgorithmException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/find/{name:.+}" )
    @ApiOperation( value = "Permite buscar un algoritmo de dispensado por nombre", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'search-algorithm' )" )
    public ResponseEntity < ResponsePayload < DispensedAlgorithm > > findByName( @PathVariable String name ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( algorithmService.findByName( name ) ) );
        } catch ( AlgorithmException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping
    @ApiOperation( value = "Permite buscar todos los algoritmos de dispensado del banco o la red Prosa", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'search-algorithm' )" )
    public ResponseEntity < ResponsePayload < List < DispensedAlgorithm > > > findByAll() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( algorithmService.findAll() ) );
        } catch ( AlgorithmException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/names" )
    @ApiOperation( value = "Permite buscar todos los algoritmos de dispensado (solo los nombres) del banco o la red Prosa", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'search-algorithm' )" )
    public ResponseEntity < ResponsePayload < List < String > > > findAllOnlyNames() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( algorithmService.findAllOnlyNames() ) );
        } catch ( AlgorithmException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/names/{fiid:.+}" )
    @ApiOperation( value = "Permite buscar todos los algoritmos de dispensado (solo los nombres) del banco o la red Prosa", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'search-algorithm' )" )
    public ResponseEntity < ResponsePayload < List < String > > > findAllOnlyNames( @PathVariable String fiid ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( algorithmService.findAllOnlyNames( fiid ) ) );
        } catch ( AlgorithmException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/update/{group:.+}/{algorithm:.+}" )
    @ApiOperation( value = "Permite actualizar el algoritmo de dispensado a un grupo de cajeros", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'update-algorithm' )" )
    public ResponseEntity < ResponsePayload < String > > updateAlgorithmByGroup( @PathVariable String group, @PathVariable String algorithm ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( algorithmService.updateGroup( group, algorithm ) ) );
        } catch ( AlgorithmException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }


}
