package mx.com.prosa.nabhi.jse.business;

import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.TransactionException;
import mx.com.prosa.nabhi.misc.model.adp.BillsModel;
import mx.com.prosa.nabhi.misc.model.jse.request.*;
import mx.com.prosa.nabhi.misc.model.jse.response.GenericProcess;

import java.util.Map;

public interface ITransactionalService {
    BillsModel cashWithAuth( CashWithdrawalModel generic ) throws TransactionException;

    void feeUseCreditDecline( CashWithdrawalModel generic );

    Generic getCommission( AtmInfo generic ) throws TransactionException, ATMException;

    Map < String, String > balInquiryAuth( Generic generic ) throws TransactionException;

    String listTrx( Generic generic ) throws TransactionException;

    GenericProcess changePin( ChangeNipModel generic ) throws TransactionException;

    GenericProcess genericSale( GenericSaleModel generic ) throws TransactionException;

    GenericProcess paymentOfTDC( GenericWithToAccount toAccount ) throws TransactionException;

    GenericProcess transfer( GenericWithToAccount toAccount ) throws TransactionException;


}
