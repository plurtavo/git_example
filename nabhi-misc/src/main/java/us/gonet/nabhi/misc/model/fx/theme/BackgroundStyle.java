package us.gonet.nabhi.misc.model.fx.theme;

import com.google.gson.GsonBuilder;

public class BackgroundStyle {

    private String backgroundImage;
    private String cover;

    public BackgroundStyle() {
        this.cover = "-fx-background-size: cover;";
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage( String backgroundImage ) {
        this.backgroundImage = backgroundImage;
    }

    public String getCover() {
        return cover;
    }

    public void setCover( String cover ) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
