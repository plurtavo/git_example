package mx.com.prosa.nabhi.misc.domain.complete.repository;

import mx.com.prosa.nabhi.misc.domain.complete.entity.UpTimeEntity;
import mx.com.prosa.nabhi.misc.domain.complete.entity.composite.UpTimeIdentity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UpTimeRepository extends JpaRepository < UpTimeEntity, UpTimeIdentity > {

    //Cambio release/monitoreoatm
    List < UpTimeEntity > findByUpTimeIdTerminalIdAndUpTimeIdDateBetweenOrderByUpTimeIdDateDesc( String terminalId, Date from, Date to, Pageable pageable );

    List < UpTimeEntity > findByUpTimeIdTerminalIdAndUpTimeIdDateBetweenAndUpTimeIsGreaterThanOrderByUpTimeIdDateDesc( String terminalId, Date from, Date to, int uptime, Pageable pageable );

    List < UpTimeEntity > findByUpTimeIdTerminalIdAndUpTimeIdDateBetweenAndUpTimeIsLessThanOrderByUpTimeIdDateDesc( String terminalId, Date from, Date to, int uptime, Pageable pageable );
    //Cambio release/monitoreoatm

}
