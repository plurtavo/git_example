package mx.com.prosa.nabhi.misc.domain.security.entity;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.domain.complete.entity.AuditTable;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@EntityListeners( AuditTable.class )
@Table( name = "TBL_USER" )
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 45, name = "PK_ID" )
    private String id;

    @Column( name = "PASSWORD", length = 100 )
    private String password;

    @Column( name = "PHONE", length = 10 )
    private String phoneNumber;

    @Column( length = 30 )
    private String name;

    @Column( length = 30 )
    private String lastName;

    @Column( name = "ISSUE_TIME" )
    private long issueTime;

    @Column( name = "EXPIRATION_TIME" )
    private long expirationTime;

    @Column( name = "EXPIRATION_TOKEN_TIME" )
    private long expirationTokenTime;

    @Column( name = "LAST_USAGE" )
    private long lastUsage;

    @Column( name = "ENABLED" )
    private boolean enabled;

    @Column( name = "ENABLE" )
    private boolean tokenExpired;

    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_FIID", foreignKey = @ForeignKey( name = "FK_OWNER_USER" ) )
    @Fetch( FetchMode.JOIN )
    private IDFEntityKey owner;

    @ManyToMany( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @Fetch( FetchMode.JOIN )
    private Set < RoleEntity > roles;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled( boolean enabled ) {
        this.enabled = enabled;
    }

    public boolean isTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired( boolean tokenExpired ) {
        this.tokenExpired = tokenExpired;
    }

    public void setLastUsage( long lastUsage ) {
        this.lastUsage = lastUsage;
    }

    public IDFEntityKey getOwner() {
        return owner;
    }

    public void setOwner( IDFEntityKey owner ) {
        this.owner = owner;
    }

    public Set < RoleEntity > getRoles() {
        return roles;
    }

    public void setRoles( Set < RoleEntity > roles ) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
