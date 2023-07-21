package mx.com.prosa.nabhi.jse.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.jse.business.ITransactionalService;
import mx.com.prosa.nabhi.jse.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.TransactionException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.adp.BillsModel;
import mx.com.prosa.nabhi.misc.model.jse.request.*;
import mx.com.prosa.nabhi.misc.model.jse.response.GenericProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping( "athz" )
@CrossOrigin( origins = { "*" }, maxAge = 6000 )
@Api( tags = { SwaggerConfig.TAG_6 } )
public class TransactionalController {

    private static final String[] ALLOWED_FIELDS = new String[]{ "termId", "ip", "txCommission",
            "fromAccount", "nip", "track", "emv", "cashWithAmount", "newPin", "confirmNewPin", "phone", "company" };

    private final ITransactionalService transactionalService;

    @Autowired
    public TransactionalController( ITransactionalService transactionalService ) {
        this.transactionalService = transactionalService;
    }

    @InitBinder( "Generic" )
    public void initBinder( WebDataBinder binder ) {
        binder.setAllowedFields( ALLOWED_FIELDS );
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/wthdw" )
    @PreAuthorize( "hasAnyAuthority( 'atm-basico', 'atm-advance', 'atm-full'  )" )
    @ApiOperation( value = "Realiza la solicitud de autorización de la transacción de retiro", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < BillsModel > > cashWithAuth( @RequestBody CashWithdrawalModel generic ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( transactionalService.cashWithAuth( generic ) ) );
        } catch ( TransactionException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/wthdw/feeDecline" )
    @PreAuthorize( "hasAnyAuthority( 'atm-basico', 'atm-advance', 'atm-full'  )" )
    @ApiOperation( value = "Realiza el reverso para retiros con uso de línea de crédito", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public void feeUseCreditDecline( @RequestBody CashWithdrawalModel generic ) {
        transactionalService.feeUseCreditDecline( generic );
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/blnInq" )
    @PreAuthorize( "hasAnyAuthority( 'atm-basico', 'atm-advance', 'atm-full'  )" )
    @ApiOperation( value = "Realiza la solicitud de autorización de la transacción de consulta de saldo", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < Map < String, String > > > balInquiryAuth( @RequestBody Generic generic ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( transactionalService.balInquiryAuth( generic ) ) );
        } catch ( TransactionException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }

    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/cmmssn" )
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Regresa la comisión a cobrar por el uso de cajero para cada transacción", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < Generic > > getCommission( @RequestBody AtmInfo atmInfo ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( transactionalService.getCommission( atmInfo ) ) );
        } catch ( TransactionException | ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/lstTrx" )
    @ApiOperation( value = "Realiza la solicitud de autorización de la transacción de consulta de movimientos", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < String > > listTrx( @RequestBody Generic generic ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( transactionalService.listTrx( generic ) ) );
        } catch ( TransactionException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/chngPNb" )
    @PreAuthorize( "hasAnyAuthority( 'atm-basico', 'atm-advance', 'atm-full'  )" )
    @ApiOperation( value = "Realiza la solicitud de autorización de la transacción de consulta de cambio de NIP", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < GenericProcess > > changeNip( @RequestBody ChangeNipModel generic ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( transactionalService.changePin( generic ) ) );
        } catch ( TransactionException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/gnrcSl" )
    @PreAuthorize( "hasAnyAuthority( 'atm-basico', 'atm-advance', 'atm-full'  )" )
    @ApiOperation( value = "Realiza la solicitud de autorización de la transacción de venta generica (VTA)", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < GenericProcess > > genericSale( @RequestBody GenericSaleModel generic ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( transactionalService.genericSale( generic ) ) );
        } catch ( TransactionException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/paymentTDC" )
    @PreAuthorize( "hasAnyAuthority( 'atm-advance', 'atm-full' )" )
    @ApiOperation( value = "Realiza la solicitud de autorización de la transacción de pago de tarjeta de crédito", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < GenericProcess > > paymentOfTDC( @RequestBody GenericWithToAccount toAccount ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( transactionalService.paymentOfTDC( toAccount ) ) );
        } catch ( TransactionException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/transfer" )
    @PreAuthorize( "hasAnyAuthority( 'atm-advance', 'atm-full' )" )
    @ApiOperation( value = "Realiza la solicitud de autorización de la transacción de traspasos", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < GenericProcess > > transfer( @RequestBody GenericWithToAccount toAccount ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( transactionalService.transfer( toAccount ) ) );
        } catch ( TransactionException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
