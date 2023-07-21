package mx.com.prosa.nabhi.jse.business.impl;

import mx.com.prosa.nabhi.jse.business.ICardService;
import mx.com.prosa.nabhi.jse.core.database.JournalOperation;
import mx.com.prosa.nabhi.jse.core.database.memory.atm.ATMSearch;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.model.jdb.ScreenGroup;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDScreen;
import mx.com.prosa.nabhi.misc.model.jse.StylesBank;
import mx.com.prosa.nabhi.misc.model.jse.request.AtmInfo;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import mx.com.prosa.nabhi.misc.model.jse.response.CardInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardService implements ICardService {

    private static final Logger LOG = LoggerFactory.getLogger( CardService.class );
    private final ATMSearch atmSearch;
    private final JournalOperation journalOperation;

    @Autowired
    public CardService( ATMSearch atmSearch, JournalOperation journalOperation ) {
        this.atmSearch = atmSearch;
        this.journalOperation = journalOperation;
    }

    @Override
    public Generic incomingCard( Generic generic ) {
        return new Generic();
    }

    @Override
    public CardInfo validatingCard( AtmInfo atmInfo ) throws ATMException {
        ATDScreen atd = atmSearch.searchScreen( atmInfo.getTermId() );
        if ( atmInfo.getTrack().length() < 16 || atmInfo.getTrack() == null ) {
            throw new ATMException( CatalogError.ATM_INVALID_TRACK_2 );
        } else {
            atmSearch.tranInProgress( atd.getTerminalId(), true );
            journalOperation.write( atd.getTerminalId(), "***BEGIN TRANSACTION***" );
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "Inicia la transacción para el cajero %s", atmInfo.getTermId() ) );
            }
            String discretionary = atmInfo.getTrack().substring( atmInfo.getTrack().indexOf( '=' ) + 1 );
            return validateCard( discretionary, atd );
        }
    }

    @Override
    public boolean cardRemoved( AtmInfo atmInfo ) throws ATMException {
        atmSearch.tranInProgress( atmInfo.getTermId(), false );
        journalOperation.write( atmInfo.getTermId(), "***END TRANSACTION***" );
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Finaliza la transacción para el cajero %s", atmInfo.getTermId() ) );
        }
        return true;

    }

    private CardInfo validateCard( String discretionary, ATDScreen atd ) {
        return cardDefault( atd, discretionary );
    }


    private CardInfo cardDefault( ATDScreen atd, String discretionary ) {
        ScreenGroup screenGroup = atd.getScreenGroup();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setBank( atd.getIdf().getName() );
        cardInfo.setMessage( getCardCapabilities( discretionary ) );
        StylesBank stylesBank = new StylesBank();
        stylesBank.setButtons( screenGroup.getButtonsStyle() );
        stylesBank.setDashboard( screenGroup.getBodyStyle() );
        stylesBank.setBackgroundImage( screenGroup.getBackGround() );
        stylesBank.setSections( "" );
        cardInfo.setStyles( stylesBank );
        return cardInfo;
    }

    private String getCardCapabilities( String discretionary ) {
        String capabilities = discretionary.substring( 4, 5 );
        if ( capabilities.equals( "2" ) || capabilities.equals( "6" ) ) {
            return "CHIP";
        } else {
            return "BAND";
        }
    }

}
