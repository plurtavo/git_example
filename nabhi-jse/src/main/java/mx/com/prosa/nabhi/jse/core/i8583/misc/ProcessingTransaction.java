package mx.com.prosa.nabhi.jse.core.i8583.misc;

import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.exception.rest.RestExchangeException;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.iso.ATMResponseModel;
import mx.com.prosa.nabhi.misc.receipt.IReceipt;
import mx.com.prosa.nabhi.misc.receipt.ResponseISOBuilder;
import mx.com.prosa.nabhi.misc.rest.iso.ISORequester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessingTransaction {

    private final ResponseISOBuilder responseISOBuilder;
    private final ISORequester isoRequester;
    private final IReceipt receipt;
    private static final Logger LOG = LoggerFactory.getLogger( ProcessingTransaction.class );

    @Autowired
    public ProcessingTransaction( ResponseISOBuilder responseISOBuilder, ISORequester isoRequester, IReceipt receipt ) {
        this.responseISOBuilder = responseISOBuilder;
        this.isoRequester = isoRequester;
        this.receipt = receipt;
    }

    public ATMResponseModel sendTransaction( ATMRequestModel atmRequestModel ) throws ISO8583Exception {
        try {
            ATMResponseModel responseModel = isoRequester.sendMessage( atmRequestModel, "/sendTransaction/{nodeName}", atmRequestModel.getNodeIso() );
            if ( !responseModel.getMessage().isTimeOut() ) {
                return responseISOBuilder.genericResponse( atmRequestModel, responseModel.getMessage(), receipt );
            } else {
                if ( LOG.isDebugEnabled() ) {
                    LOG.debug( String.format( "Timeout por parte de B24 %s", atmRequestModel.getSequenceNumber() ) );
                }
                throw new ISO8583Exception( CatalogError.RESPONSE_TIMEOUT );
            }
        } catch ( RestExchangeException e ) {
            throw new ISO8583Exception( e.getError(), "Causa hubo un error con los servicios" );
        }
    }


}
