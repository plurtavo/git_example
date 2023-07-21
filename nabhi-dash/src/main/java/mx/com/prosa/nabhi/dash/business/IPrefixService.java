package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.Prefix;

import java.util.List;

public interface IPrefixService {

    String create( Prefix value ) throws DataBaseException;

    String modify( Prefix value ) throws DataBaseException;

    String delete( Prefix value ) throws DataBaseException, IDFException;

    Prefix findById( String bin, int panLen, int binLen ) throws DataBaseException;

    List < Prefix > findAll() throws DataBaseException, IDFException;

    List < String > findAllOnlyNames() throws DataBaseException, IDFException;


}
