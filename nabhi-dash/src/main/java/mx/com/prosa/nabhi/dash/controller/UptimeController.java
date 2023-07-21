package mx.com.prosa.nabhi.dash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.IUptimeService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.dash.model.uptime.ReportSearchConditions;
import mx.com.prosa.nabhi.dash.model.uptime.UptimeReport;
import mx.com.prosa.nabhi.dash.report.BookJsonUptime;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.dash.UptimeException;
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

@RestController
@RequestMapping( "uptime" )
@Api( tags = { SwaggerConfig.TAG_1 } )
public class UptimeController {

    private final IUptimeService uptimeService;

    @Autowired
    public UptimeController( IUptimeService uptimeService ) {
        this.uptimeService = uptimeService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/raw" )
    @ApiOperation( value = "Permite consultar un reporte crudo JSON de disponibilidad", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-uptime' )" )
    public ResponseEntity < ResponsePayload < UptimeReport > > getReportRaw( @RequestBody ReportSearchConditions reportSearchConditions ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( uptimeService.getReportRaw( reportSearchConditions ) ) );
        } catch ( UptimeException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/raw/excel" )
    @ApiOperation( value = "Permite consultar un reporte crudo EXCEL de disponibilidad", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-uptime' )" )
    public ResponseEntity < ByteArrayResource > getReportRawExcel( @RequestBody ReportSearchConditions reportSearchConditions ) {
        try {
            ByteArrayOutputStream stream = uptimeService.getReportRawExcel( reportSearchConditions );
            HttpHeaders header = new HttpHeaders();
            header.setContentType( new MediaType( "application", "force-download" ) );
            header.set( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_cajero.xlsx" );
            return new ResponseEntity <>( new ByteArrayResource( stream.toByteArray() ),
                    header, HttpStatus.CREATED );
        } catch ( UptimeException e ) {
            return new ResponseEntity <>( HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/report" )
    @ApiOperation( value = "Permite consultar un reporte procesado JSON de disponibilidad", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-uptime' )" )
    public ResponseEntity < ResponsePayload < BookJsonUptime > > getReport( @RequestBody ReportSearchConditions reportSearchConditions ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( uptimeService.getReport( reportSearchConditions ) ) );
        } catch ( UptimeException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/excel" )
    @ApiOperation( value = "Permite consultar un reporte procesado EXCEL de disponibilidad", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-uptime' )" )
    public ResponseEntity < ByteArrayResource > getReportExcel( @RequestBody ReportSearchConditions reportSearchConditions ) {
        try {
            ByteArrayOutputStream stream = uptimeService.getReportExcel( reportSearchConditions );
            HttpHeaders header = new HttpHeaders();
            header.setContentType( new MediaType( "application", "force-download" ) );
            header.set( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_de_cajeros.xlsx" );
            return new ResponseEntity <>( new ByteArrayResource( stream.toByteArray() ),
                    header, HttpStatus.CREATED );
        } catch ( UptimeException e ) {
            return new ResponseEntity <>( HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

}
