package us.gonet.nabhi.misc.model.fx.theme;

import com.google.gson.GsonBuilder;

public class ButtonStyle {

    private String color;
    private String backgroundColor;
    private String width;
    private String height;
    private String fontFamily;
    private String borderRadius;
    private String fontSize;
    private String border;
    private String cursor;

    public ButtonStyle() {
    }

    public ButtonStyle( ButtonStyle b ) {
        this.color = b.getColor();
        this.backgroundColor = b.getBackgroundColor();
        this.width = b.getWidth();
        this.height = b.getHeight();
        this.fontFamily = b.getFontFamily();
        this.borderRadius = b.getBorderRadius();
        this.fontSize = b.getFontSize();
        this.border = b.getBorder();
        this.cursor = b.getCursor();
    }


    public String getColor() {
        return color;
    }

    public void setColor( String color ) {
        this.color = color;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor( String backgroundColor ) {
        this.backgroundColor = backgroundColor;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth( String width ) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight( String height ) {
        this.height = height;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily( String fontFamily ) {
        this.fontFamily = fontFamily;
    }

    public String getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius( String borderRadius ) {
        this.borderRadius = borderRadius;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize( String fontSize ) {
        this.fontSize = fontSize;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder( String border ) {
        this.border = border;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor( String cursor ) {
        this.cursor = cursor;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
