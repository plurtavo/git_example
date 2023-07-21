package mx.com.prosa.nabhi.jse.business;

import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.model.jke.TmkEntity;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;


public interface ITPKExchangeService {

    TmkEntity exchange( Generic atmIp ) throws ATMException;
}
