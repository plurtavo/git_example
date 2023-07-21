package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.NodeTCP;

import java.util.List;

public interface INodeService {

    String create( NodeTCP value ) throws DataBaseException;

    String modify( NodeTCP value ) throws DataBaseException;

    String delete( NodeTCP value ) throws DataBaseException, IDFException;

    NodeTCP findById( String name ) throws DataBaseException;

    List < NodeTCP > findAll() throws DataBaseException, IDFException;

    List < String > findAllOnlyNames() throws DataBaseException, IDFException;


}
