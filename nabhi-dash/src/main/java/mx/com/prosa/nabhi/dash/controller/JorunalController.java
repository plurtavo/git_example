package mx.com.prosa.nabhi.dash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.IJournalService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jdb.Journal;
import mx.com.prosa.nabhi.misc.model.journal.JournalQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "journal" )
@Api( tags = { SwaggerConfig.TAG_14 } )
public class JorunalController {

    private final IJournalService iJournalService;

    @Autowired
    public JorunalController( IJournalService iJournalService ) {
        this.iJournalService = iJournalService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping
    @ApiOperation( "Permite realizar una consulta de Journal dado ciertos criterios" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-journal' )" )
    public ResponseEntity < ResponsePayload < List < Journal > > > find( @RequestBody JournalQuery journalQuery ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( iJournalService.find( journalQuery ) ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/{terminalId:.+}" )
    @ApiOperation( "Permite realizar una consulta de Journal por cajero" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'read-journal' )" )
    public ResponseEntity < ResponsePayload < List < Journal > > > find( @PathVariable String terminalId ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( iJournalService.find( terminalId ) ) );
        } catch ( DataBaseException | IDFException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
