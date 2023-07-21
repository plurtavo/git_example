package mx.com.prosa.nabhi.dash.controller;
//Cambio release/redcat

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.com.prosa.nabhi.dash.business.IUserService;
import mx.com.prosa.nabhi.dash.config.SwaggerConfig;
import mx.com.prosa.nabhi.dash.security.ldap.LDAPException;
import mx.com.prosa.nabhi.misc.alert.Alert;
import mx.com.prosa.nabhi.misc.exception.sanitize.SanitazeException;
import mx.com.prosa.nabhi.misc.exception.user.AccessControlException;
import mx.com.prosa.nabhi.misc.log.UserAccess;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jdb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/user" )
@Api( tags = { SwaggerConfig.TAG_6 } )
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController( IUserService userService ) {
        this.userService = userService;
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/create" )
    @ApiOperation( "Permite crear un usuario en la base de datos (solo si existe en LDAP)" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > create( @RequestBody User tokenUser ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.create( tokenUser, false ) ) );
        } catch ( AccessControlException | SanitazeException | LDAPException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/modify" )
    @ApiOperation( "Permite modificar un usuario en la base de datos (solo si existe en LDAP)" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > modify( @RequestBody User tokenUser ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.modify( tokenUser, false ) ) );
        } catch ( AccessControlException | SanitazeException | LDAPException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/delete" )
    @ApiOperation( "Permite eliminar un usuario en la base de datos (solo si no existe en LDAP)" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > delete( @RequestBody User tokenUser ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.delete( tokenUser, false ) ) );
        } catch ( AccessControlException | LDAPException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/enable" )
    @ApiOperation( "Permite habilitar un usuario en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > enable( @RequestBody User tokenUser ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.enable( tokenUser ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/disable" )
    @ApiOperation( "Permite deshabilitar un usuario en la base de datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > disable( @RequestBody User tokenUser ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.disable( tokenUser ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/findAll" )
    @ApiOperation( "Devuelve todos los usuarios de la Base de Datos" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < List < User > > > findAll() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.findAll() ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/findAllNames" )
    @ApiOperation( "Devuelve todos los usuarios de la Base de Datos, solo los LDAP UID" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < List < String > > > findAllNames() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.findAllNames() ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/findById/{id:.+}" )
    @ApiOperation( "Devuelve el usuario especificado" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < User > > findById( @PathVariable String id ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.findById( id ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/roles" )
    @ApiOperation( "Devuelve todos los Roles" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < List < Role > > > roles() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.getRoles() ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @DeleteMapping( "/role" )
    @ApiOperation( "Permite eliminar un Rol" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > deleteRole( @RequestBody Role role ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.deleteRole( role ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PutMapping( "/role" )
    @ApiOperation( "Permite crear un nuevo Rol" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > createRole( @RequestBody Role role ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.createRole( role ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/role" )
    @ApiOperation( "Modifica un Rol existente" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > updateRole( @RequestBody Role role ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.updateRole( role ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    //Cambio release/eventos    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/privileges" )
    @ApiOperation( "Devuelve todos los privilegios" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < List < Privilege > > > privileges() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.getPrivileges() ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @DeleteMapping( "/privilege" )
    @ApiOperation( "Permite eliminar un privilegio" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > deletePrivilege( @RequestBody Privilege privilege ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.deletePrivilegie( privilege ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PutMapping( "/privilege" )
    @ApiOperation( "Permite crear un nuevo privilegio" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > createPrivilege( @RequestBody Privilege privilege ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.createPrivilege( privilege ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PutMapping( "/privilege/bacth" )
    @ApiOperation( "Crea un nuevos privilegios en batch" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > createPrivilege( @RequestBody List < Privilege > privileges ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.createPrivilege( privileges ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/menu" )
    @ApiOperation( "Permite agregar un menú" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > addMenus( @RequestBody List < MenuObject > menus ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.addMenus( menus ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/menu" )
    @ApiOperation( "Devuelve todos los menus" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < List < MenuObject > > > getMenus() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.getMenus() ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @PostMapping( "/roles/menu" )
    @ApiOperation( "Permite asociar menus a un rol" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > updateRoleMenu( @RequestBody Role role ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.updateRoleMenu( role ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/roles/menu" )
    @ApiOperation( "Muestra los menus asociados a un rol" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < List < Role > > > updateRoleMenu() {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.getRoleMenu() ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @DeleteMapping( "/menu" )
    @ApiOperation( "Permite eliminar un menú" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'user-security' )" )
    public ResponseEntity < ResponsePayload < String > > deleteMenu( @RequestBody MenuObject menuObject ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.deleteMenu( menuObject ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/log/{username:.+}" )
    @ApiOperation( "Devuelve los eventos de un usuario" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'log' )" )
    public ResponseEntity < ResponsePayload < List < LogEvent > > > viewLog( @PathVariable String username ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.viewLogs( username ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/log/{from:.+}/{to:.+}" )
    @ApiOperation( "Devuelve los eventos de todos los usuarios con fechas inicio y fin" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'log' )" )
    public ResponseEntity < ResponsePayload < List < LogEvent > > > viewLog( @PathVariable String from, @PathVariable String to ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.viewLogs( from, to ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Alert
    @UserAccess
    @ResponseBody
    @GetMapping( "/log/{from:.+}/{to:.+}/{username:.+}" )
    @ApiOperation( "evuelve los eventos de un usuario con fechas inicio y fin" )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Operación correcta" ),
            @ApiResponse( code = 403, message = "Permiso denegado" ),
            @ApiResponse( code = 500, message = "Error en la operación" ) } )
    @PreAuthorize( "hasAnyAuthority( 'log' )" )
    public ResponseEntity < ResponsePayload < List < LogEvent > > > viewLog( @PathVariable String from, @PathVariable String to, @PathVariable String username ) {
        try {
            return ResponseEntity.ok( ResponsePayload.of( userService.viewLogs( from, to, username ) ) );
        } catch ( AccessControlException e ) {
            return new ResponseEntity <>( ResponsePayload.setError( e.getMessage(), e.getError().getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    //Cambio release/eventos
}
