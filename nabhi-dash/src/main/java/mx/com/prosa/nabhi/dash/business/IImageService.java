package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.Image;

import java.util.List;

public interface IImageService {

    String create( Image value ) throws DataBaseException;

    String modify( Image value ) throws DataBaseException;

    String delete( Image value ) throws DataBaseException, IDFException;

    Image findById( String name, String category ) throws DataBaseException;

    List < Image > findAll() throws DataBaseException, IDFException;

    List < String > findAllOnlyNames() throws DataBaseException, IDFException;


}
