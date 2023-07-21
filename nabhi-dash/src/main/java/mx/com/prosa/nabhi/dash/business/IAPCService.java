package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.APC;

import java.util.List;

public interface IAPCService {

    String create( APC value ) throws DataBaseException;

    String modify( APC value ) throws DataBaseException;

    String delete( APC value ) throws DataBaseException, IDFException;

    APC findById( String fiid, String from, String to, String tranCode ) throws DataBaseException;

    List < APC > findAll() throws DataBaseException, IDFException;

    List < String > findAllOnlyNames() throws DataBaseException, IDFException;


}
