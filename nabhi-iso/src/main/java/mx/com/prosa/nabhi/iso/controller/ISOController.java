package mx.com.prosa.nabhi.iso.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.iso.business.IISOService;
import mx.com.prosa.nabhi.iso.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.iso.ATMResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import us.gonet.serializable.data.ISO;

@RestController
@RequestMapping()
@Api( tags = { SwaggerConfig.TAG_1 } )
public class ISOController {

    private final IISOService service;

    @Autowired
    public ISOController( IISOService service ) {
        this.service = service;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/sendTransaction/{nodeName:.+}" )
    @PreAuthorize( "hasAnyAuthority( 'transactional' )" )
    @ApiOperation( value = "Generates an ISO-8583 1987 Type 0200 message and routes it to the specified HISO Base 24 Interface", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < ATMResponseModel > > sendTransaction( @PathVariable String nodeName, @RequestBody ATMRequestModel request ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( service.sendTransaction( request, nodeName ) ) );
        } catch ( ISO8583Exception | SocketException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/sendReversal/{nodeName:.+}" )
    @PreAuthorize( "hasAnyAuthority( 'transactional' )" )
    @ApiOperation( value = "Generates an ISO-8583 1987 Type 0420 message and routes it to the specified HISO Base 24 Interface", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < ATMResponseModel > > sendTransaction( @PathVariable String nodeName, @RequestBody ISO request ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( service.sendReversal( request, nodeName ) ) );
        } catch ( ISO8583Exception | SocketException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

}
