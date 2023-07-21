package mx.com.prosa.nabhi.jse.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.jse.business.ICardService;
import mx.com.prosa.nabhi.jse.config.SwaggerConfig;
import mx.com.prosa.nabhi.jse.core.database.memory.atm.ATMSearch;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jse.request.AtmInfo;
import mx.com.prosa.nabhi.misc.model.jse.response.CardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "crdrdr" )
@CrossOrigin( origins = { "*" }, maxAge = 6000 )
@Api( tags = { SwaggerConfig.TAG_4 } )
public class CardReaderController {

    private final ATMSearch atmSearch;
    private final ICardService business;

    @Autowired
    public CardReaderController( ATMSearch atmSearch, ICardService business ) {
        this.atmSearch = atmSearch;
        this.business = business;
    }

    private static final String[] ALLOWED_FIELDS = new String[]{ "ip", "track", "termId" };

    @InitBinder( "AtmInfo" )
    public void initBinder( WebDataBinder binder ) {
        binder.setAllowedFields( ALLOWED_FIELDS );
    }


    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/crdincmg" )
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Identifica el inicio de transacción al ingresar la tarjeta y valida si el cajero existe", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta card" ),
            @ApiResponse( code = 403, message = "Permiso denegado card" ),
            @ApiResponse( code = 500, message = "Error en la operación card" ) } )
    public ResponseEntity < ResponsePayload < Boolean > > atmVerification( @RequestBody AtmInfo generic ) {
        try {
            atmSearch.searchBasic( generic.getTermId() );
            return ResponseEntity.ok( ResponsePayload.of( true ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/crdvldt" )
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Valida las capacidades de la tarjeta ingresada", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < CardInfo > > validateCard( @RequestBody AtmInfo atmInfo ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( business.validatingCard( atmInfo ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/crdrmvd" )
    @ApiOperation( value = "Identifica el fin de transacción al retirar la tarjeta", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < Boolean > > cardRemoved( @RequestBody AtmInfo atmInfo ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( business.cardRemoved( atmInfo ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
