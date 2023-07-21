package mx.com.prosa.nabhi.jse.business.impl;

import mx.com.prosa.nabhi.jse.business.IValidationService;
import mx.com.prosa.nabhi.jse.core.adp.Dispensed;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.model.adp.BillsModel;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Cassette;
import mx.com.prosa.nabhi.misc.model.jse.request.CashWithdrawalModel;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationService implements IValidationService {

    private final Dispensed dispensed;

    @Autowired
    public ValidationService( Dispensed dispensed ) {
        this.dispensed = dispensed;
    }

    @Override
    public BillsModel validateWithdrawal( CashWithdrawalModel generic ) throws ATMException {
        return dispensed.dispenseFourUnits( generic.getTermId(), Integer.parseInt( generic.getCashWithAmount() ) );
    }

    @Override
    public Cassette validateMinAmount( Generic generic ) throws ATMException {
        return dispensed.getMinimumAmount( generic.getTermId() );
    }

}
