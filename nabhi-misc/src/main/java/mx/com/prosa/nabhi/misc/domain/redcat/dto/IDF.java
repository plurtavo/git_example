package mx.com.prosa.nabhi.misc.domain.redcat.dto;

import com.google.gson.GsonBuilder;

public class IDF {

    private String fiid;

    private String currentBusinessDay;

    private String nextBusinessDay;

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
