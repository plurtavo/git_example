package mx.com.prosa.nabhi.jse.core;

import java.util.List;

public class SupervisorAdvice {

    private String termId;
    private List< String > messages;

    public String getTermId() {
        return termId;
    }

    public void setTermId( String termId ) {
        this.termId = termId;
    }

    public List < String > getMessages() {
        return messages;
    }

    public void setMessages( List < String > messages ) {
        this.messages = messages;
    }

}
