package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.exception.ReceiptException;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.RCPT;

import java.util.List;

public interface IRCPTService {

    String create( RCPT value ) throws DataBaseException;

    String modify( RCPT value ) throws DataBaseException;

    String delete( RCPT value ) throws DataBaseException;

    RCPT findById( String name ) throws DataBaseException;

    List < RCPT > findAll() throws DataBaseException;

    List < String > findAllOnlyNames() throws DataBaseException;

    String test( String name ) throws DataBaseException, ReceiptException;
}
