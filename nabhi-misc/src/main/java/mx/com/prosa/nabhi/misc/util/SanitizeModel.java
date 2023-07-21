package mx.com.prosa.nabhi.misc.util;

import mx.com.prosa.nabhi.misc.exception.sanitize.SanitazeException;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Cassette;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Dispenser;
import mx.com.prosa.nabhi.misc.model.devices.ptr.Printer;
import mx.com.prosa.nabhi.misc.model.jdb.Package;
import mx.com.prosa.nabhi.misc.model.jdb.*;
import mx.com.prosa.nabhi.misc.model.jdb.composite.SurchargeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.gonet.nabhi.misc.model.jdb.blob.ButtonMapping;
import us.gonet.nabhi.misc.model.jdb.blob.FileWrapper;


@Component
public class SanitizeModel {

    private final StreamFilter filter;

    @Autowired
    public SanitizeModel( StreamFilter filter ) {
        this.filter = filter;
    }

    public void sanitize( String s ) throws SanitazeException {
        filter.sanitizeString( s );
    }

    public void sanitize( APC a ) throws SanitazeException {
        a.setFiid( filter.sanitizeString( a.getFiid() ) );
        a.setFormAcct( filter.sanitizeString( a.getFormAcct() ) );
        a.setToAcct( filter.sanitizeString( a.getToAcct() ) );
        a.setTranCode( filter.sanitizeString( a.getTranCode() ) );
        sanitize( a.getAllowedCode() );
        a.setRoutingGroup( filter.sanitizeString( a.getRoutingGroup() ) );
        a.setSharingGroup( filter.sanitizeString( a.getSharingGroup() ) );
    }


    public void sanitize( ATD a ) throws SanitazeException {
        a.setTerminalId( filter.sanitizeString( a.getTerminalId() ) );
        a.setSequence( filter.sanitizeString( a.getSequence() ) );
        a.setDeviceType( filter.sanitizeString( a.getDeviceType() ) );
        a.setPostingDay( filter.sanitizeString( a.getPostingDay() ) );
        a.setIp( filter.sanitizeString( a.getIp() ) );
        a.setLastTrx( filter.sanitizeString( a.getLastTrx() ) );
        a.setLocation( filter.sanitizeString( a.getLocation() ) );
        a.setReceipt( filter.sanitizeString( a.getReceipt() ) );
        sanitize( a.getIdf() );
        sanitize( a.getCounty() );
        sanitize( a.getScreenGroup() );
        sanitize( a.getNodeHiso() );
        sanitize( a.getNodeMtvk() );
    }

    public void sanitize( State e ) throws SanitazeException {
        e.setStateCode( filter.sanitizeString( e.getStateCode() ) );
        e.setStateName( filter.sanitizeString( e.getStateName() ) );
        e.setStateShortName( filter.sanitizeString( e.getStateShortName() ) );
        e.setZone( filter.sanitizeString( e.getZone() ) );
    }


    public void sanitize( County e ) throws SanitazeException {
        e.setCountyCode( filter.sanitizeString( e.getCountyCode() ) );
        e.setCountyName( filter.sanitizeString( e.getCountyName() ) );
    }

    public void sanitize( Country e ) throws SanitazeException {
        e.setCountryCode( filter.sanitizeString( e.getCountryCode() ) );
        e.setName( filter.sanitizeString( e.getName() ) );
        e.setAlpha2( filter.sanitizeString( e.getAlpha2() ) );
        e.setAlpha3( filter.sanitizeString( e.getAlpha3() ) );
        e.setSymbols( filter.sanitizeString( e.getSymbols(), "[a-zA-Z0-9 .=*/_" + e.getSymbols() + "]+" ) );
    }

    public void sanitize( IDF a ) throws SanitazeException {
        a.setFiid( filter.sanitizeString( a.getFiid() ) );
        a.setLogicalNet( filter.sanitizeString( a.getLogicalNet() ) );
        a.setName( filter.sanitizeString( a.getName() ) );
        a.setAcquiringId( filter.sanitizeString( a.getAcquiringId() ) );
        a.setNameLong( filter.sanitizeString( a.getNameLong(), "[a-zA-Z0-9 . =*< /_,()]+" ) );
        a.setCurrentBusinessDay( filter.sanitizeString( a.getCurrentBusinessDay() ) );
        a.setNextBusinessDay( filter.sanitizeString( a.getNextBusinessDay() ) );
        a.setAgreement( filter.sanitizeString( a.getAgreement() ) );
    }


    public void sanitize( TranAllowed e ) throws SanitazeException {
        e.setAllowedCode( filter.sanitizeString( e.getAllowedCode() ) );
    }

    public void sanitize( NodeTCP e ) throws SanitazeException {
        e.setIp( filter.sanitizeString( e.getIp() ) );
        e.setZpk( filter.sanitizeString( e.getZpk() ) );
        e.setController( filter.sanitizeString( e.getController() ) );
    }

    public void sanitize( Surcharge a ) throws SanitazeException {
        sanitize( a.getSurchargeId() );
        a.setSurcharges( filter.sanitizeString( a.getSurcharges() ) );
    }

    private void sanitize( SurchargeId a ) throws SanitazeException {
        a.setFiidAcquirer( filter.sanitizeString( a.getFiidAcquirer() ) );
        a.setFiidIssuing( filter.sanitizeString( a.getFiidIssuing() ) );
        a.setTranCode( filter.sanitizeString( a.getTranCode() ) );
    }


    public void sanitize( ScreenGroup e ) throws SanitazeException {
        final String REGEX = "[a-zA-Z0-9 .=*:,<\"%/_{}()#]+";
        e.setGroupId( filter.sanitizeString( e.getGroupId() ) );
        e.setPublicityName( filter.sanitizeString( e.getPublicityName() ) );
        e.setBackGround( filter.sanitizeString( e.getBackGround(), REGEX ) );
        e.setButtonsStyle( filter.sanitizeString( e.getButtonsStyle(), REGEX ) );
        e.setBodyStyle( filter.sanitizeString( e.getBodyStyle(), REGEX ) );
    }

    public void sanitize( ButtonMapping e ) throws SanitazeException {
        e.setBitmap( filter.sanitizeString( e.getBitmap() ) );
        e.setScreenComponent( filter.sanitizeString( e.getScreenComponent() ) );
    }

    public void sanitize( Dispenser e ) throws SanitazeException {
        e.setStatus( Integer.parseInt( filter.sanitizeString( "" + e.getStatus() ) ) );
        //Cambio para sanitizar casetera lógica y fisica
        //Angel Serralde López 26/06/2021 - REDCAT
        for ( Cassette c : e.getCassettes() ) {
            sanitize( c );
        }
    }


    private void sanitize( Cassette e ) throws SanitazeException {
        //Cambio release/redcat
        e.setCurrency( filter.sanitizeString( e.getCurrency() ) );
        //Cambio release/algoritmos
        e.getPhysical().setStatus( filter.sanitizeString( e.getPhysical().getStatus() ) );
        //Cambio release/algoritmos
        e.setType( filter.sanitizeString( e.getType() ) );
        //Cambio release/redcat
    }


    public void sanitize( Printer e ) throws SanitazeException {
        e.setPaper( filter.sanitizeString( e.getPaper() ) );
        e.setToner( filter.sanitizeString( e.getToner() ) );
        e.setStatus( Integer.parseInt( filter.sanitizeString( "" + e.getStatus() ) ) );
    }

    public void sanitize( Journal e ) throws SanitazeException {
        e.setMessage( filter.sanitizeString( e.getMessage() ) );
        e.setTerminalId( filter.sanitizeString( e.getTerminalId() ) );
    }

    public void sanitize( RCPT e ) throws SanitazeException {
        final String REGEX = "[a-zA-Z0-9 -.=*:,/ %#@\\[.*.\\]_]+";
        e.setHeader( filter.sanitizeString( e.getHeader(), REGEX ) );
        e.setBody( filter.sanitizeString( e.getBody(), REGEX ) );
        e.setTrailer( filter.sanitizeString( e.getTrailer(), REGEX ) );
    }

    public void sanitize( Image e ) throws SanitazeException {
        e.setName( filter.sanitizeString( e.getName() ) );
        e.setCategory( filter.sanitizeString( e.getCategory() ) );
        sanitize( e.getWrapper() );
    }

    private void sanitize( FileWrapper e ) throws SanitazeException {
        e.setName( filter.sanitizeString( e.getName() ) );
        e.setExtension( filter.sanitizeString( e.getExtension() ) );
        e.setFormat( filter.sanitizeString( e.getFormat() ) );
        e.setCodec( filter.sanitizeString( e.getCodec(), "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$" ) );
    }

    public void sanitize( BankStyle e ) throws SanitazeException {
        final String REGEX = "[a-zA-Z0-9 .=*:,<\"%/_{}()#]+";
        e.setId( filter.sanitizeString( e.getId() ) );
        e.setButtons( filter.sanitizeString( e.getButtons(), REGEX ) );
        e.setDashboard( filter.sanitizeString( e.getDashboard(), REGEX ) );
        e.setBackgroundImage( filter.sanitizeString( e.getBackgroundImage(), REGEX ) );
    }

    public void sanitize( User e ) throws SanitazeException {
        e.setId( filter.sanitizeString( e.getId(), "[a-zA-Z0-9 -.=*:,/ %#@\\[.*.\\]_]+" ) );
        e.setP( filter.sanitizeString( e.getP() ) );
        e.setPhoneNumber( filter.sanitizeString( e.getPhoneNumber() ) );
        e.setName( filter.sanitizeString( e.getName() ) );
        e.setLastName( filter.sanitizeString( e.getLastName() ) );
    }

    public void sanitize( Role e ) throws SanitazeException {
        e.setName( filter.sanitizeString( e.getName() ) );
    }

    public void sanitize( Privilege e ) throws SanitazeException {
        e.setName( filter.sanitizeString( e.getName() ) );
    }

    public void sanitize( Package e ) throws SanitazeException {
        e.setName( filter.sanitizeString( e.getName() ) );
        for ( mx.com.prosa.nabhi.misc.model.jdb.Component component : e.getComponents() ) {
            sanitize( component );
        }
    }

    public void sanitize( mx.com.prosa.nabhi.misc.model.jdb.Component e ) throws SanitazeException {
        e.setName( filter.sanitizeString( e.getName() ) );
    }

}
