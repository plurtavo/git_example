package mx.com.prosa.nabhi.dash.business;
//Cambio release/redcat

import mx.com.prosa.nabhi.dash.model.SearchConditions;
import mx.com.prosa.nabhi.dash.model.monitoring.MonitoringView;
import mx.com.prosa.nabhi.misc.exception.dash.MonitoringException;

public interface IMonitoringService {

    MonitoringView getReport( SearchConditions searchConditions ) throws MonitoringException;

}
