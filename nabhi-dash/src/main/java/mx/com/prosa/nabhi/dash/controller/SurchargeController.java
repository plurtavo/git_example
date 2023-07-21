package mx.com.prosa.nabhi.dash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.ISurchargeService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.dash.core.IInstitutionService;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jdb.Surcharge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "surcharge/" )
@Api( tags = { SwaggerConfig.TAG_17 } )
public class SurchargeController {

    private final ISurchargeService surchargeService;
    private final IInstitutionService institutionService;
    private static final String FIID_PROSA = "PROS";

    @Autowired
    public SurchargeController( ISurchargeService surchargeService, IInstitutionService institutionService ) {
        this.surchargeService = surchargeService;
        this.institutionService = institutionService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PutMapping
    @ApiOperation( "Permite crear un surcharge en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'create-surcharge' )" )
    public ResponseEntity < ResponsePayload < String > > create( @RequestBody Surcharge value ) {
        try {
            IDFEntityKey key = institutionService.lookupUser();
            if ( !key.getFiid().equals( FIID_PROSA ) ) {
                value.getSurchargeId().setFiidAcquirer( key.getFiid() );
            }
            return ResponseEntity.ok( ResponsePayload.of( surchargeService.create( value ) ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping
    @ApiOperation( "Permite modificar un surcharge en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'write-surcharge' )" )
    public ResponseEntity < ResponsePayload < String > > modify( @RequestBody Surcharge value ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( surchargeService.modify( value ) ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @DeleteMapping
    @ApiOperation( "Permite eliminar un surcharge en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'delete-surcharge' )" )
    public ResponseEntity < ResponsePayload < String > > delete( @RequestBody Surcharge value ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( surchargeService.delete( value ) ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "find/{fiidB:.+}/{tranCode:.+}" )
    @ApiOperation( "Permite buscar un surcharge en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-surcharge' )" )
    public ResponseEntity < ResponsePayload < Surcharge > > findsurcharge( @PathVariable String fiidB, @PathVariable String tranCode, @RequestParam( required = false ) String fiid ) {
        try {
            IDFEntityKey key = institutionService.lookupUser();
            if ( !key.getFiid().equals( FIID_PROSA ) ) {
                fiid = key.getFiid();
            }
            return ResponseEntity.ok( ResponsePayload.of( surchargeService.findById( fiid, fiidB, tranCode ) ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping
    @ApiOperation( "Permite buscar todos los surcharge en la base de datos de acuerdo al FIID del usuario" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-type' )" )
    public ResponseEntity < ResponsePayload < List < Surcharge > > > findAll() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( surchargeService.findAll() ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "names" )
    @ApiOperation( "Permite buscar todos los surcharge en la base de datos de acuerdo al FIID del usuario" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-type' )" )
    public ResponseEntity < ResponsePayload < List < String > > > findAllOnlyName() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( surchargeService.findAllOnlyNames() ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
