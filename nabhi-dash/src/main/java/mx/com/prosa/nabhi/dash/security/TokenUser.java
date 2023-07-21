package mx.com.prosa.nabhi.dash.security;

import mx.com.prosa.nabhi.misc.model.jdb.onlykeys.IDFKey;

public class TokenUser {

    private String name;
    private long issueTime;
    private long expirationTime;
    private long expirationTokenTime;
    private long lastUsage;
    private IDFKey owner;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
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
}