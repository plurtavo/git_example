package mx.com.prosa.nabhi.misc.domain.redcat.entity;

import com.google.gson.GsonBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table( name = "TBL_IDF" )
public class IDFForRedcatEntity implements Serializable {

    @Id
    @Column( length = 4, name = "PK_FIID" )
    private String fiid;

    @Column( length = 10, name = "CURRENT_BUSINESS_DAY" )
    private String currentBusinessDay;

    @Column( length = 10, name = "NEXT_BUSINESS_DAY" )
    private String nextBusinessDay;

    @Column( length = 5, name = "FORCED_CUT_OVER" )
    private String forcedCutOver;

    public String getFiid() {
        return fiid;
    }

    public void setFiid(String fiid) {
        this.fiid = fiid;
    }

    public String getCurrentBusinessDay() {
        return currentBusinessDay;
    }

    public void setCurrentBusinessDay(String currentBusinessDay) {
        this.currentBusinessDay = currentBusinessDay;
    }

    public String getNextBusinessDay() {
        return nextBusinessDay;
    }

    public void setNextBusinessDay(String nextBusinessDay) {
        this.nextBusinessDay = nextBusinessDay;
    }

    public String getForcedCutOver() {
        return forcedCutOver;
    }

    public void setForcedCutOver(String forcedCutOver) {
        this.forcedCutOver = forcedCutOver;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
