package mx.com.prosa.nabhi.jse.business;

import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.model.jse.request.AtmInfo;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import mx.com.prosa.nabhi.misc.model.jse.response.CardInfo;

public interface ICardService {

    Generic incomingCard( Generic generic ) throws ATMException;

    CardInfo validatingCard( AtmInfo atmInfo ) throws ATMException;

    boolean cardRemoved( AtmInfo atmInfo ) throws ATMException;
}
