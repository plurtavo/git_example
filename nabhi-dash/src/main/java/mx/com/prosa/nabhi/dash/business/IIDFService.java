package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.IDF;

import java.util.List;

public interface IIDFService {

    String create( IDF value ) throws DataBaseException;

    String modify( IDF value ) throws DataBaseException;

    String delete( IDF value ) throws DataBaseException, IDFException;

    IDF findById( String fiid ) throws DataBaseException;

    List < IDF > findAll() throws DataBaseException, IDFException;

    List < String > findAllOnlyNames() throws DataBaseException, IDFException;


}
