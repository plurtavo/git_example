package mx.com.prosa.nabhi.jse.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.jse.business.ITPKExchangeService;
import mx.com.prosa.nabhi.jse.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jke.TmkEntity;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "tmkkey" )
@CrossOrigin( origins = { "*" }, maxAge = 6000 )
@Api( tags = { SwaggerConfig.TAG_2 } )
public class ATMTerminalPinKeyController {

    private final ITPKExchangeService terminalPinKey;
    private static final String[] ALLOWED_FIELDS = new String[]{ "ip" };

    @InitBinder( "Generic" )
    public void initBinder( WebDataBinder binder ) {
        binder.setAllowedFields( ALLOWED_FIELDS );
    }

    @Autowired
    public ATMTerminalPinKeyController( ITPKExchangeService terminalPinKey ) {
        this.terminalPinKey = terminalPinKey;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/atmData" )
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < TmkEntity > > incomingAtm( @RequestBody Generic atm ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( terminalPinKey.exchange( atm ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
