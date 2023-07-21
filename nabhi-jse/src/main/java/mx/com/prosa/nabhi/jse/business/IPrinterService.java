package mx.com.prosa.nabhi.jse.business;

import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import mx.com.prosa.nabhi.misc.model.jse.response.Ticket;

public interface IPrinterService {

    Ticket printingTicket( Generic generic ) throws ATMException;

}
