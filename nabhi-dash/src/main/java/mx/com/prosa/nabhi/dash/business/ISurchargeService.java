package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.Surcharge;

import java.util.List;

public interface ISurchargeService {

    String create( Surcharge value ) throws DataBaseException;

    String modify( Surcharge value ) throws DataBaseException;

    String delete( Surcharge value ) throws DataBaseException, IDFException;

    Surcharge findById( String fiidA, String fiidB, String tranCode ) throws DataBaseException;

    List < Surcharge > findAll() throws DataBaseException, IDFException;

    List < String > findAllOnlyNames() throws DataBaseException, IDFException;


}
