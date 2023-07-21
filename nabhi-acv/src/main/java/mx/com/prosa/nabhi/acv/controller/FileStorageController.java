package mx.com.prosa.nabhi.acv.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.acv.config.SwaggerConfig;
import mx.com.prosa.nabhi.acv.storage.StorageService;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.acv.FileException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping( "file/" )
@Api( tags = { SwaggerConfig.TAG_2 } )
public class FileStorageController {

    private final StorageService storageService;

    @Autowired
    public FileStorageController( StorageService storageService ) {
        this.storageService = storageService;
    }

    @PostMapping( "upload" )
    @ApiOperation( value = "Permite consultar un reporte crudo JSON de disponibilidad", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < String > uploadFile2( @RequestParam( "file" ) MultipartFile file,
                                                  @RequestParam( value = "terminalId" ) String terminalId ) {
        try {
            storageService.init( "/" + terminalId );
            return ResponseEntity.ok( storageService.store( file, terminalId ) );
        } catch ( FileException e ) {
            return new ResponseEntity <>( e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @GetMapping( "download/{path:.+}/{fileName:.+}" )
    @ApiOperation( value = "Permite consultar un reporte crudo JSON de disponibilidad", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < Resource > downloadFile( @PathVariable String fileName, @PathVariable String path, HttpServletRequest request ) {
        try {
            Resource stream = storageService.loadAsResource( fileName, path );
            HttpHeaders header = new HttpHeaders();
            header.setContentType( new MediaType( "application", "force-download" ) );
            header.set( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path + ".png" );
            return new ResponseEntity <>( stream,
                    header, HttpStatus.CREATED );
        } catch ( FileException e ) {
            return new ResponseEntity <>( HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @DeleteMapping( "delete/{path:.+}" )
    public ResponseEntity < String > delete( @PathVariable String path ) {
        try {
            storageService.delete( path );
            return ResponseEntity.ok( "OK" );
        } catch ( FileException e ) {
            return new ResponseEntity <>( e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
