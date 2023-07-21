package mx.com.prosa.nabhi.dash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.impl.RedcatService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.dash.model.redcat.RedcatSearchConditions;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.domain.redcat.dto.Redcat;
import mx.com.prosa.nabhi.misc.exception.dash.RedcatException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping( "redcat" )
@Api( tags = { SwaggerConfig.TAG_4 } )
public class RedcatController {

    private final RedcatService redcatService;

    @Autowired
    public RedcatController( RedcatService redcatService ) {
        this.redcatService = redcatService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/force/atm" )
    @ApiOperation( value = "Permite forzar el corte a un cajero", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'write-redcat' )" )
    public ResponseEntity < ResponsePayload < String > > forceCutByATM( @RequestBody RedcatSearchConditions conditions ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( redcatService.forceCutForATD( conditions ) ) );
        } catch ( RedcatException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/force/fiid" )
    @ApiOperation( value = "Permite forzar el corte para una FIID", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'write-redcat' )" )
    public ResponseEntity < ResponsePayload < String > > forceCutByFIID( @RequestBody RedcatSearchConditions conditions ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( redcatService.forceCutForFIID( conditions ) ) );
        } catch ( RedcatException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/getAll" )
    @ApiOperation( value = "Permite obtener un reporte redcat en formato JSON", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-redcat' )" )
    public ResponseEntity < ResponsePayload < List < Redcat > > > getAll( @RequestBody RedcatSearchConditions conditions ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( redcatService.findRedcat( conditions ) ) );
        } catch ( RedcatException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/excel" )
    @ApiOperation( value = "Permite obtener un reporte redcat en excel", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-redcat' )" )
    public ResponseEntity < ByteArrayResource > getRedcatExcel( @RequestBody RedcatSearchConditions conditions ) {
        try {
            ByteArrayOutputStream stream = redcatService.findExcelRedcat( conditions );
            HttpHeaders header = new HttpHeaders();
            header.setContentType( new MediaType( "application", "force-download" ) );
            header.set( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=redcat.xlsx" );
            return new ResponseEntity <>( new ByteArrayResource( stream.toByteArray() ),
                    header, HttpStatus.CREATED );
        } catch ( RedcatException e ) {
            return new ResponseEntity <>( HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

}
