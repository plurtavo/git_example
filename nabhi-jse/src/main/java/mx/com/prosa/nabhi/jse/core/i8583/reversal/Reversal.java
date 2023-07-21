package mx.com.prosa.nabhi.jse.core.i8583.reversal;

import mx.com.prosa.nabhi.jse.core.database.JournalOperation;
import mx.com.prosa.nabhi.jse.core.database.memory.atm.ATMSearch;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDTransactional;
import mx.com.prosa.nabhi.misc.model.jse.request.AtmNotificationModel;
import mx.com.prosa.nabhi.misc.model.reversal.ATMReversalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.gonet.iso8583.constants.ReversalCodes;

@Component
public class Reversal {

    private static final Logger LOG = LoggerFactory.getLogger( Reversal.class );

    private final ReversalHandler reversalHandler;
    private final JournalOperation journalOperation;
    private final ATMSearch atmSearch;

    @Autowired
    public Reversal( ReversalHandler reversalHandler, ATMSearch atmSearch, JournalOperation journalOperation ) {
        this.reversalHandler = reversalHandler;
        this.atmSearch = atmSearch;
        this.journalOperation = journalOperation;
    }

    public void makeReversal( AtmNotificationModel model, int amount ) throws ATMException {
        journalOperation.write( model.getTermId(), "Monto dispensado :" + amount );
        String reverseType = "Reverso Parcial";
        if ( amount == 0 ) {
            reverseType = "Reverso Total";
        }
        if ( amount != 0 ) {
            reverseType = "Reverso Total";
            amount = 0;
            if ( LOG.isInfoEnabled() ) {
                LOG.info( String.format( "No se permiten reversos parciales %d", amount ) );
            }
        }
        try {
            buildReversal( model.getTermId(), amount, ReversalCodes.SUSPECTED_MALFUNCTION );
            String response = reverseType + " Exitoso";
            if ( LOG.isInfoEnabled() ) {
                LOG.info( response );
            }
            journalOperation.write( model.getTermId(), response );
        } catch ( ISO8583Exception e ) {
            journalOperation.write( model.getTermId(), "Hubo un error al realizar el " + reverseType + e.getError().getMessage() );
            throw new ATMException( e.getError() );
        }

    }

    public void makeReversal( String terminalId, int amount, ReversalCodes reversalCode ) {
        try {
            buildReversal( terminalId, amount, reversalCode );
            journalOperation.write( terminalId, "No se completo la transaccion con QuickPay" );
            if ( LOG.isErrorEnabled() ) {
                LOG.error( String.format( "No se completo la transacción con QP %s", terminalId ) );
            }
        } catch ( ISO8583Exception e ) {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( String.format( "Respuesta invalida de B24 %s", e.getMessage() ) );
            }
            journalOperation.write( terminalId, "Invalid response from Base 24" );
        } catch ( ATMException e ) {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( "Cajero no encontrado" );
            }
        }
    }

    private void buildReversal( String terminalId, int amount, ReversalCodes reversalCodes ) throws ATMException, ISO8583Exception {
        ATDTransactional atd = atmSearch.searchTransactional( terminalId );
        ATMReversalModel atmReversalModel = new ATMReversalModel();
        atmReversalModel.setMessage( atd.getLastTrx() );
        atmReversalModel.setDispensedAmount( amount );
        atmReversalModel.setReversalCode( reversalCodes.getValue() );
        reversalHandler.sendMessage( atmReversalModel, atd.getNode().getNodeName() );
    }
}
