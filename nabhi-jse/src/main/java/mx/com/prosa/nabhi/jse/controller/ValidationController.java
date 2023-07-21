package mx.com.prosa.nabhi.jse.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.jse.business.IValidationService;
import mx.com.prosa.nabhi.jse.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.adp.BillsModel;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Cassette;
import mx.com.prosa.nabhi.misc.model.jse.request.CashWithdrawalModel;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "vldtn" )
@CrossOrigin( origins = { "*" }, maxAge = 6000 )
@Api( tags = { SwaggerConfig.TAG_7 } )
public class ValidationController {

    private static final String[] ALLOWED_FIELDS = new String[]{ "ip", "cashWithAmount" };
    private final IValidationService business;

    @InitBinder( "Generic" )
    public void initBinder( WebDataBinder binder ) {
        binder.setAllowedFields( ALLOWED_FIELDS );
    }

    @Autowired
    public ValidationController( IValidationService business ) {
        this.business = business;
    }

    @PostMapping( "wthdw" )
    @Alert
    @UserAccess
    @ResponseBody
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Valida que el monto a dispensar sea valido para el cajero según el estado de las caseteras", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < BillsModel > > validateWithdrawal( @RequestBody CashWithdrawalModel generic ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( business.validateWithdrawal( generic ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @PostMapping( "mntMnm" )
    @Alert
    @UserAccess
    @ResponseBody
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Valida el monto minímo que se puede dispensar según las caseteras disponibles", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < Cassette > > validateMinAmount( @RequestBody Generic generic ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( business.validateMinAmount( generic ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

}
