package mx.com.prosa.nabhi.misc.domain.complete.repository;

import mx.com.prosa.nabhi.misc.domain.complete.entity.JournalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface JournalRepository extends JpaRepository < JournalEntity, String > {

    List < JournalEntity > findByWriteDateBetween( Timestamp from, Timestamp to );

    List < JournalEntity > findByTerminalIdOrderByIdDesc( String terminalId );

    List < JournalEntity > findByTerminalIdAndWriteDateBetweenOrderByIdDesc( String terminal, Timestamp from, Timestamp to );

}
