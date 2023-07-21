package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IJournalService;
import mx.com.prosa.nabhi.dash.core.IInstitutionService;
import mx.com.prosa.nabhi.misc.domain.complete.entity.JournalEntity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.JournalRepository;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.Journal;
import mx.com.prosa.nabhi.misc.model.journal.JournalQuery;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class JournalService implements IJournalService {

    private final JournalRepository journalRepository;
    private final ModelMapper mapper;
    private final Type listType = new TypeToken < List < Journal > >() {
    }.getType();

    @Autowired
    public JournalService( JournalRepository journalRepository, ModelMapper mapper, IInstitutionService institutionService ) {
        this.journalRepository = journalRepository;
        this.mapper = mapper;
    }

    @Override
    public List < Journal > find( JournalQuery journalQuery ) throws DataBaseException, IDFException {
        List < JournalEntity > entities;
        entities = journalRepository.findByTerminalIdAndWriteDateBetweenOrderByIdDesc( journalQuery.getTerminalId(), journalQuery.getFrom(), journalQuery.getTo() );
        if ( !entities.isEmpty() ) {
            return mapper.map( entities, listType );
        } else {
            throw new DataBaseException( CatalogError.LOG_NOT_FOUND );
        }
    }

    @Override
    public List < Journal > find( String terminalId ) throws DataBaseException, IDFException {
        List < JournalEntity > entities = journalRepository.findByTerminalIdOrderByIdDesc( terminalId );
        if ( !entities.isEmpty() ) {
            return mapper.map( entities, listType );
        } else {
            throw new DataBaseException( CatalogError.LOG_NOT_FOUND );
        }
    }
}
