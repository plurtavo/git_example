package mx.com.prosa.nabhi.dash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.IBatchService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.acv.FileException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping( "batch/" )
@Api( tags = { SwaggerConfig.TAG_18 } )
public class BatchController {

    private final IBatchService batchService;

    @Autowired
    public BatchController( IBatchService batchService ) {
        this.batchService = batchService;
    }

    @Alert
    @UserAccess
    @PostMapping( "upload" )
    @ApiOperation( value = "Permite realizar la carga masiva de manera dinámica", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    //@PreAuthorize( "hasAnyAuthority( 'batch' )" )
    public ResponseEntity < ResponsePayload < String > > uploadFile2( @RequestParam( "file" ) MultipartFile file ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( batchService.batch( file ) ) );
        } catch ( FileException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }


    @UserAccess
    @ResponseBody
    @GetMapping( "/example/{path:.+}/{file:.+}" )
    @ApiOperation( value = "Permite consultar ejemplos para cargas masivas", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    //@PreAuthorize( "hasAnyAuthority( 'batch' )" )
    public ResponseEntity < Resource > getReportRawExcel( @PathVariable String path, @PathVariable String file ) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType( new MediaType( "application", "force-download" ) );
            header.set( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file + "-" + path + ".yml" );
            return new ResponseEntity <>( batchService.loadAsResource( "abc/" + path + "/" + file + ".yml" ), header, HttpStatus.CREATED );
        } catch ( FileException e ) {
            return new ResponseEntity <>( HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }


}
