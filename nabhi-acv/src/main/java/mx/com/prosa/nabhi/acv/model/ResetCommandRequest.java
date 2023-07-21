package mx.com.prosa.nabhi.acv.model;

import com.google.gson.GsonBuilder;

public class ResetCommandRequest {

    private int command;

    public int getCommand() {
        return command;
    }

    public void setCommand( int command ) {
        this.command = command;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
