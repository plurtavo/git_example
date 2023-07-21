package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.ScreenGroup;

import java.util.List;

public interface IScreenService {

    String create( ScreenGroup value ) throws DataBaseException;

    String modify( ScreenGroup value ) throws DataBaseException;

    String delete( ScreenGroup value ) throws DataBaseException, IDFException;

    ScreenGroup findById( String screenName ) throws DataBaseException;

    List < ScreenGroup > findAll() throws DataBaseException, IDFException;

    List < String > findAllOnlyNames() throws DataBaseException, IDFException;

    List < String > findAllOnlyNames( String fiid ) throws DataBaseException, IDFException;

    String updateGroup( String groupName, String screen ) throws DataBaseException, IDFException;


}
