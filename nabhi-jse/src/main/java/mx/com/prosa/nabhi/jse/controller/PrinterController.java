package mx.com.prosa.nabhi.jse.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.jse.business.IPrinterService;
import mx.com.prosa.nabhi.jse.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import mx.com.prosa.nabhi.misc.model.jse.response.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "prntrcntrllr" )
@CrossOrigin( origins = { "*" }, maxAge = 6000 )
@Api( tags = { SwaggerConfig.TAG_5 } )
public class PrinterController {

    private final IPrinterService business;

    private static final String[] ALLOWED_FIELDS = new String[]{ "ip", "termId" };

    @InitBinder( "Generic" )
    public void initBinder( WebDataBinder binder ) {
        binder.setAllowedFields( ALLOWED_FIELDS );
    }

    @Autowired
    public PrinterController( IPrinterService business ) {
        this.business = business;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/prntng" )
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Regresa cadena de caracteres con el ticket formateado listo para su impresión", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < Ticket > > printingTicket( @RequestBody Generic generic ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( business.printingTicket( generic ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
