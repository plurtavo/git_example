package mx.com.prosa.nabhi.dash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.IMonitoringService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.dash.model.SearchConditions;
import mx.com.prosa.nabhi.dash.model.monitoring.MonitoringView;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.dash.MonitoringException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "monitoring" )
@Api( tags = { SwaggerConfig.TAG_2 } )
public class MonitoringController {

    private final IMonitoringService monitoringService;

    @Autowired
    public MonitoringController( IMonitoringService monitoringService ) {
        this.monitoringService = monitoringService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/view" )
    @ApiOperation( value = "Permite consultar en tiempo si uno o mas cajeros están en linea o no", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security', 'monitoring' )" )
    public ResponseEntity < ResponsePayload < MonitoringView > > getReportRaw( @RequestBody SearchConditions searchConditions ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( monitoringService.getReport( searchConditions ) ) );
        } catch ( MonitoringException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

}