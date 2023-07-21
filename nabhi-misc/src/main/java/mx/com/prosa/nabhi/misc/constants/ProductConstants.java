package mx.com.prosa.nabhi.misc.constants;



public enum ProductConstants {
    AMEX ("3"),
    ABI ("X"),
    APCA("X"),
    CUP("X"),
    DISCOVER("X"),
    EUROPAY("X"),
    JCB("X"),
    LINK("X"),
    MASTERCARD("5"),
    MULTIBANCO("X"),
    VISA("4"),
    RUPAY("X"),
    CB("X"),
    BCARD("X"),
    UNKNOWN("UNKNOWN")
    ;

    private final String bin;

    ProductConstants(String bin) {
        this.bin = bin;
    }

    public static ProductConstants getValue (String name ) {
        return ProductConstants.valueOf( name );
    }

    public static ProductConstants getByBin(String value){
        for(ProductConstants e: values()){
            if ( e.getBin().equals( value ) ){
                return e;
            }
        }
        return UNKNOWN;
    }

    public String getBin() {
        return bin;
    }

}
