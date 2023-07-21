package mx.com.prosa.nabhi.iso.core.builder.token;


public enum TokenID {

    TOKEN_PS( "PS" ),
    TOKEN_B2( "B2" ),
    TOKEN_B3( "B3" ),
    TOKEN_B4( "B4" ),
    TOKEN_06( "06" ),
    TOKEN_P1( "P1" ),
    TOKEN_QV( "QV" ),
    TOKEN_RC( "RC" ),

    ;
    private String id;

    TokenID( String id ) {
        this.id = id;
    }
}
