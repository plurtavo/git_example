package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.Journal;
import mx.com.prosa.nabhi.misc.model.journal.JournalQuery;

import java.util.List;

public interface IJournalService {

    List < Journal > find( JournalQuery journalQuery ) throws DataBaseException, IDFException;

    List < Journal > find( String terminalId ) throws DataBaseException, IDFException;

}
