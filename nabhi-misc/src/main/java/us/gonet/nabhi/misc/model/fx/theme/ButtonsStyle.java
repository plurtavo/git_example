package us.gonet.nabhi.misc.model.fx.theme;

import com.google.gson.GsonBuilder;

public class ButtonsStyle {

    private ButtonStyle style;
    private ButtonStyle styleHover;
    private ButtonStyle styleDisabled;

    public ButtonStyle getStyle() {
        return style;
    }

    public void setStyle( ButtonStyle style ) {
        this.style = style;
    }

    public ButtonStyle getStyleHover() {
        return styleHover;
    }

    public void setStyleHover( ButtonStyle styleHover ) {
        this.styleHover = styleHover;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

    public ButtonStyle getStyleDisabled() {
        return styleDisabled;
    }

    public void setStyleDisabled( ButtonStyle styleDisabled ) {
        this.styleDisabled = styleDisabled;
    }
}
