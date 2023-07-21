package mx.com.prosa.nabhi.jse.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.jse.business.ICapabilitiesService;
import mx.com.prosa.nabhi.jse.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jdb.Image;
import mx.com.prosa.nabhi.misc.model.jdb.ScreenGroup;
import mx.com.prosa.nabhi.misc.model.jse.Publicity;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import mx.com.prosa.nabhi.misc.model.jse.response.ScreenCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "capa" )
@CrossOrigin( origins = { "*" }, maxAge = 6000 )
@Api( tags = { SwaggerConfig.TAG_3 } )
public class CapabilitiesControl {

    private final ICapabilitiesService iCapabilitiesService;

    private static final String[] ALLOWED_FIELDS = new String[]{ "ip" };

    @InitBinder( "Generic" )
    public void initBinder( WebDataBinder binder ) {
        binder.setAllowedFields( ALLOWED_FIELDS );
    }

    @Autowired
    public CapabilitiesControl( ICapabilitiesService iCapabilitiesService ) {
        this.iCapabilitiesService = iCapabilitiesService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/bilities" )
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Obtiene las capacidades de cada pantalla del cajero y botones activos en cada una", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < List < ScreenCapabilities > > > returnCapabilities( @RequestBody Generic generic ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( iCapabilitiesService.getButtons( generic ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/publicity" )
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Obtiene la publicidad que muestra el cajero en la pantalla inicial", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < Publicity > > returnPublicity( @RequestBody Generic generic ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( iCapabilitiesService.getPublicity( generic ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/screen" )
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Obtiene la imagen de fondo del cajero", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < ScreenGroup > > getAllScreen( @RequestBody Generic generic ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( iCapabilitiesService.getAllScreen( generic ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/image/{id:.+}/{category:.+}" )
    @PreAuthorize( "hasAnyAuthority( 'atm' )" )
    @ApiOperation( value = "Regresa la imagen solicitada en un formato de Base64", produces = "application/json" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    public ResponseEntity < ResponsePayload < Image > > findImage( @PathVariable String id, @PathVariable String category ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( iCapabilitiesService.findImage( id, category ) ) );
        } catch ( ATMException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

}
