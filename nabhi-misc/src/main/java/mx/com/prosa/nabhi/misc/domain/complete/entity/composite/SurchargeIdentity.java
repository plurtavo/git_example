package mx.com.prosa.nabhi.misc.domain.complete.entity.composite;

import com.google.gson.GsonBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SurchargeIdentity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Column( length = 4, name = "FIID_ACQUIRER" )
    private String fiidAcquirer;

    @Column( length = 4, name = "FIID_ISSUING" )
    private String fiidIssuing;

    @Column( length = 2, name = "TRAN_CODE" )
    private String tranCode;

    public SurchargeIdentity( String fiidAcquirer, String fiidIssuing, String tranCode ) {
        this.fiidAcquirer = fiidAcquirer;
        this.fiidIssuing = fiidIssuing;
        this.tranCode = tranCode;
    }

    public SurchargeIdentity() {
    }

    public String getFiidAcquirer() {
        return fiidAcquirer;
    }

    public void setFiidAcquirer( String fiidAcquirer ) {
        this.fiidAcquirer = fiidAcquirer;
    }

    public String getFiidIssuing() {
        return fiidIssuing;
    }

    public void setFiidIssuing( String fiidIssuing ) {
        this.fiidIssuing = fiidIssuing;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode( String tranCode ) {
        this.tranCode = tranCode;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
