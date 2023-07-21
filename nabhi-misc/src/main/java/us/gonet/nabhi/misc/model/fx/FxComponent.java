package us.gonet.nabhi.misc.model.fx;

import java.util.ArrayList;
import java.util.List;

public enum FxComponent {

    ORIENTATION,
    POSITION,
    LABEL,
    IMAGE_VIEW,
    SINGLE_TEXT_FIELD,
    COMPOSE_TEXT_FIELD,
    SINGLE_PASSWORD_FIELD,
    COMPOSE_PASSWORD_FIELD,
    TABLE_VIEW,
    LIST_VIEW,
    CAROUSEL,
    EMPTY,
    INVALID;

    FxComponent() {
    }

    public static FxComponent valueOfCompose( String name ) {
        try {
            return FxComponent.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return INVALID;
        }
    }

    public static List < String > valuesList() {
        List < String > value = new ArrayList <>();
        for ( FxComponent f : FxComponent.values() ) {
            if ( !f.equals( INVALID ) ) {
                value.add( f.name() );
            }
        }
        return value;
    }
}
