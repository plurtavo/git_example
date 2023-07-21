package us.gonet.nabhi.misc.model.fx.theme;

import com.google.gson.GsonBuilder;

public class LabelStyle {

    private String fontFamily;
    private String color;
    private int size;

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily( String fontFamily ) {
        this.fontFamily = fontFamily;
    }

    public String getColor() {
        return color;
    }

    public void setColor( String color ) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize( int size ) {
        this.size = size;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
