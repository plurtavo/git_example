package mx.com.prosa.nabhi.jse.business;

import mx.com.prosa.nabhi.jse.core.SupervisorAdvice;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.model.jse.request.AtmNotificationModel;

public interface IATMNotifyService {

    boolean updateDevice( AtmNotificationModel model ) throws ATMException;

    String sendToDevice( AtmNotificationModel model ) throws ATMException;

    String supervisorAdvice( SupervisorAdvice advice ) throws ATMException;

    //Cambio release/redcat
    boolean endowment( AtmNotificationModel model ) throws ATMException;
}
