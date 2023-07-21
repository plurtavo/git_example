package us.gonet.nabhi.misc.model.devices.constants.cdm;


public enum CDMError {

    /*
     * @since v3.00
     */
    INVALIDCURRENCY( -300L ),
    /*
     * @since v3.00
     */
    INVALIDTELLERID( -301L ),
    /*
     * @since v3.00
     */
    CASHUNITERROR( -302L ),
    /*
     * @since v3.00
     */
    INVALIDDENOMINATION( -303L ),
    /*
     * @since v3.00
     */
    INVALIDMIXNUMBER( -304L ),
    /*
     * @since v3.00
     */
    NOCURRENCYMIX( -305L ),
    /*
     * @since v3.00
     */
    NOTDISPENSABLE( -306L ),
    /*
     * @since v3.00
     */
    TOOMANYITEMS( -307L ),
    /*
     * @since v3.00
     */
    UNSUPPOSITION( -308L ),
    /*
     * @since v3.00
     */
    SAFEDOOROPEN( -310L ),
    /*
     * @since v3.00
     */
    SHUTTERNOTOPEN( -312L ),
    /*
     * @since v3.00
     */
    SHUTTEROPEN( -313L ),
    /*
     * @since v3.00
     */
    SHUTTERCLOSED( -314L ),
    /*
     * @since v3.00
     */
    INVALIDCASHUNIT( -315L ),
    /*
     * @since v3.00
     */
    NOITEMS( -316L ),
    /*
     * @since v3.00
     */
    EXCHANGEACTIVE( -317L ),
    /*
     * @since v3.00
     */
    NOEXCHANGEACTIVE( -318L ),
    /*
     * @since v3.00
     */
    SHUTTERNOTCLOSED( -319L ),
    /*
     * @since v3.00
     */
    PRERRORNOITEMS( -320L ),
    /*
     * @since v3.00
     */
    PRERRORITEMS( -321L ),
    /*
     * @since v3.00
     */
    PRERRORUNKNOWN( -322L ),
    /*
     * @since v3.00
     */
    ITEMSTAKEN( -323L ),
    /*
     * @since v3.00
     */
    INVALIDMIXTABLE( -327L ),
    /*
     * @since v3.00
     */
    OUTPUTPOS_NOT_EMPTY( -328L ),
    /*
     * @since v3.00
     */
    INVALIDRETRACTPOSITION( -329L ),
    /*
     * @since v3.00
     */
    NOTRETRACTAREA( -330L ),
    /*
     * @since v3.00
     */
    NOCASHBOXPRESENT( -333L ),
    /*
     * @since v3.00
     */
    AMOUNTNOTINMIXTABLE( -334L ),
    /*
     * @since v3.00
     */
    ITEMSNOTTAKEN( -335L ),
    /*
     * @since v3.00
     */
    ITEMSLEFT( -336L ),
    /*
     * @since v3.10
     */
    INVALID_PORT( -337L ),
    /*
     * @since v3.10
     */
    POWERSAVETOOSHORT( -338L ),
    /*
     * @since v3.10
     */
    POWERSAVEMEDIAPRESENT( -339L ),
    /*
     * @since v3.30
     */
    POSITION_NOT_EMPTY( -340L ),
    /*
     * @since v3.30
     */
    INCOMPLETERETRACT( -341L ),
    /*
     * @since v3.30
     */
    COMMANDUNSUPP( -342L ),
    /*
     * @since v3.30
     */
    SYNCHRONIZEUNSUPP( -343L ),
    NA( 0L );

    private final long value;

    CDMError( final long value ) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static CDMError valueOfCompose( String name ) {
        try {
            return CDMError.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NA;
        }
    }
}
