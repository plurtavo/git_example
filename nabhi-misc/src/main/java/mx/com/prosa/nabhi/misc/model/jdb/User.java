package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.onlykeys.IDFKey;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de un usuario " )
public class User implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "ID del usuario", example = "testQA" )
    private String id;
    @ApiModelProperty( value = "Contraseña", example = "13245678" )
    private String p;
    @ApiModelProperty( value = "Numero de telefono", example = "0001234567" )
    private String phoneNumber;
    @ApiModelProperty( value = "Nombre del usuario", example = "user" )
    private String name;
    @ApiModelProperty( value = "Apellido", example = "1" )
    private String lastName;
    @ApiModelProperty( value = "Timestamp de Emision del Usuario", example = "1625150383" )
    private long issueTime;
    @ApiModelProperty( value = "Timestamp de expiración del usuario", example = "1656710152" )
    private long expirationTime;
    @ApiModelProperty( value = "Timestamp de expiración del Token", example = "86400" )
    private long expirationTokenTime;
    @ApiModelProperty( value = "Timestamp del ultimo uso", example = "1624645739" )
    private long lastUsage;
    @ApiModelProperty( value = "FIID dueño del usuario", example = "B000" )
    private IDFKey owner;
    @ApiModelProperty( value = "Rol del usuario", example = "ADMNISTRADOR" )
    private List< Role > roles;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getP() {
        return p;
    }

    public void setP( String p ) {
        this.p = p;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    public long getIssueTime() {
        return issueTime;
    }

    public void setIssueTime( long issueTime ) {
        this.issueTime = issueTime;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime( long expirationTime ) {
        this.expirationTime = expirationTime;
    }

    public long getExpirationTokenTime() {
        return expirationTokenTime;
    }

    public void setExpirationTokenTime( long expirationTokenTime ) {
        this.expirationTokenTime = expirationTokenTime;
    }

    public long getLastUsage() {
        return lastUsage;
    }

    public void setLastUsage( long lastUsage ) {
        this.lastUsage = lastUsage;
    }

    public IDFKey getOwner() {
        return owner;
    }

    public void setOwner( IDFKey owner ) {
        this.owner = owner;
    }

    public List < Role > getRoles() {
        return roles;
    }

    public void setRoles( List < Role > roles ) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
