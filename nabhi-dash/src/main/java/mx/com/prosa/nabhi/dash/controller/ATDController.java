package mx.com.prosa.nabhi.dash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.IATDService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.dash.core.IInstitutionService;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jdb.ATD;
import mx.com.prosa.nabhi.misc.model.jdb.IDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "atd/" )
@Api( tags = { SwaggerConfig.TAG_9 } )
public class ATDController {

    private final IATDService service;
    private final IInstitutionService institutionService;
    private static final String FIID_PROSA = "PROS";

    @Autowired
    public ATDController( IATDService service, IInstitutionService institutionService ) {
        this.service = service;
        this.institutionService = institutionService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PutMapping
    @ApiOperation( "Permite crear un ATD en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'create-atd' )" )
    public ResponseEntity < ResponsePayload < String > > create( @RequestBody ATD value ) {
        try {
            IDFEntityKey key = institutionService.lookupUser();
            if ( !key.getFiid().equals( FIID_PROSA ) ) {
                IDF idf = new IDF();
                idf.setFiid( key.getFiid() );
                value.setIdf( idf );
            }
            return ResponseEntity.ok( ResponsePayload.of( service.create( value ) ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping
    @ApiOperation( "Permite modificar un ATD en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'write-atd' )" )
    public ResponseEntity < ResponsePayload < String > > modify( @RequestBody ATD value ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( service.modify( value ) ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @DeleteMapping
    @ApiOperation( "Permite eliminar un ATD en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'delete-atd' )" )
    public ResponseEntity < ResponsePayload < String > > delete( @RequestBody ATD value ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( service.delete( value ) ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "find/{terminalId:.+}" )
    @ApiOperation( "Permite buscar un ATD en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-atd' )" )
    public ResponseEntity < ResponsePayload < ATD > > findATD( @PathVariable String terminalId ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( service.findById( terminalId ) ) );
        } catch ( DataBaseException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping
    @ApiOperation( "Permite buscar todos los ATD en la base de datos de acuerdo al FIID del usuario" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-type' )" )
    public ResponseEntity < ResponsePayload < List < ATD > > > findAll() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( service.findAll() ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "names" )
    @ApiOperation( "Permite buscar todos los ATD en la base de datos de acuerdo al FIID del usuario" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-type' )" )
    public ResponseEntity < ResponsePayload < List < String > > > findAllOnlyName() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( service.findAllOnlyNames() ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
