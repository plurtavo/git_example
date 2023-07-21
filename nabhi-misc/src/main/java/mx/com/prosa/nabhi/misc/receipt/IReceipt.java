package mx.com.prosa.nabhi.misc.receipt;

import mx.com.prosa.nabhi.misc.exception.ReceiptException;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.receipt.Receipt;
import us.gonet.serializable.data.ISO;

public interface IReceipt {

    Receipt createReceipt( ATMRequestModel ar, ISO i, boolean costumer) throws ReceiptException;
}
