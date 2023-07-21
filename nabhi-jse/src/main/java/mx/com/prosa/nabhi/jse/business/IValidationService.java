package mx.com.prosa.nabhi.jse.business;

import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.model.adp.BillsModel;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Cassette;
import mx.com.prosa.nabhi.misc.model.jse.request.CashWithdrawalModel;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;

public interface IValidationService {

    BillsModel validateWithdrawal( CashWithdrawalModel generic ) throws ATMException;

    Cassette validateMinAmount( Generic generic ) throws ATMException;

}
