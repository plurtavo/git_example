package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.ATD;

import java.util.List;

public interface IATDService {

    String create( ATD value ) throws DataBaseException;

    String modify( ATD value ) throws DataBaseException;

    String delete( ATD value ) throws DataBaseException, IDFException;

    ATD findById( String terminalId ) throws DataBaseException;

    List < ATD > findAll() throws DataBaseException, IDFException;

    List < String > findAllOnlyNames() throws DataBaseException, IDFException;


}
