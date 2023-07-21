package mx.com.prosa.nabhi.dash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.IPrefixService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.dash.core.IInstitutionService;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jdb.Prefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "bin/" )
@Api( tags = { SwaggerConfig.TAG_10 } )
public class PrefixController {

    private final IPrefixService prefixService;
    private final IInstitutionService institutionService;
    private static final String FIID_PROSA = "PROS";

    @Autowired
    public PrefixController( IPrefixService prefixService, IInstitutionService institutionService ) {
        this.prefixService = prefixService;
        this.institutionService = institutionService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PutMapping
    @ApiOperation( "Permite crear un BIN en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'create-bin' )" )
    public ResponseEntity < ResponsePayload < String > > create( @RequestBody Prefix value ) {
        try {
            IDFEntityKey key = institutionService.lookupUser();
            if ( !key.getFiid().equals( FIID_PROSA ) ) {
                value.setFiid( key.getFiid() );
            }
            return ResponseEntity.ok( ResponsePayload.of( prefixService.create( value ) ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping
    @ApiOperation( "Permite modificar un BIN en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'write-bin' )" )
    public ResponseEntity < ResponsePayload < String > > modify( @RequestBody Prefix value ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( prefixService.modify( value ) ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @DeleteMapping
    @ApiOperation( "Permite eliminar un BIN en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'delete-bin' )" )
    public ResponseEntity < ResponsePayload < String > > delete( @RequestBody Prefix value ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( prefixService.delete( value ) ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "find/{bin:.+}/{panLen:.+}/{binLen:.+}" )
    @ApiOperation( "Permite buscar un BIN en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-bin' )" )
    public ResponseEntity < ResponsePayload < Prefix > > findBIN( @PathVariable String bin, @PathVariable int panLen, @PathVariable int binLen ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( prefixService.findById( bin, panLen, binLen ) ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping
    @ApiOperation( "Permite buscar todos los BIN en la base de datos de acuerdo al FIID del usuario" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-type' )" )
    public ResponseEntity < ResponsePayload < List < Prefix > > > findAll() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( prefixService.findAll() ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "names" )
    @ApiOperation( "Permite buscar todos los BIN en la base de datos de acuerdo al FIID del usuario" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-type' )" )
    public ResponseEntity < ResponsePayload < List < String > > > findAllOnlyName() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( prefixService.findAllOnlyNames() ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
