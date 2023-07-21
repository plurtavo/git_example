package us.gonet.nabhi.misc.model.devices.constants.idc;


public enum IDCError {

    MEDIAJAM( -200L ),
    NOMEDIA( -201L ),
    MEDIARETAINED( -202L ),
    RETAINBINFULL( -203L ),
    INVALIDDATA( -204L ),
    INVALIDMEDIA( -205L ),
    FORMNOTFOUND( -206L ),
    FORMINVALID( -207L ),
    DATASYNTAX( -208L ),
    SHUTTERFAIL( -209L ),
    SECURITYFAIL( -210L ),
    PROTOCOLNOTSUPP( -211L ),
    ATRNOTOBTAINED( -212L ),
    INVALIDKEY( -213L ),
    WRITE_METHOD( -214L ),
    CHIPPOWERNOTSUPP( -215L ),
    CARDTOOSHORT( -216L ),
    CARDTOOLONG( -217L ),
    INVALID_PORT( -218L ),
    POWERSAVETOOSHORT( -219L ),
    POWERSAVEMEDIAPRESENT( -220L ),
    CARDPRESENT( -221L ),
    POSITIONINVALID( -222L ),
    INVALIDTERMINALDATA( -223L ),
    INVALIDAIDDATA( -224L ),
    INVALIDKEYDATA( -225L ),
    READERNOTCONFIGURED( -226L ),
    TRANSACTIONNOTINITIATED( -227L ),
    COMMANDUNSUPP( -228L ),
    SYNCHRONIZEUNSUPP( -229L ),
    NA( -299L );

    private final long value;

    IDCError( final long value ) {
        this.value = value;
    }

    public static IDCError valueOfCompose( String name ) {
        try {
            return IDCError.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NA;
        }
    }
}

