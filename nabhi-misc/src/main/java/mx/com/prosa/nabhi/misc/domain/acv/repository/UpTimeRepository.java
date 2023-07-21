package mx.com.prosa.nabhi.misc.domain.acv.repository;

import mx.com.prosa.nabhi.misc.domain.acv.entity.UpTimeEntity;
import mx.com.prosa.nabhi.misc.domain.acv.entity.composite.UpTimeIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface UpTimeRepository extends JpaRepository < UpTimeEntity, UpTimeIdentity > {

    List < UpTimeEntity > findByUpTimeIdTerminalIdAndUpTimeIdDateBetween( String terminalId, Date from, Date to );

    //List < UpTimeEntity > findByUpTimeIdTerminalIdAndUpTimeIdDateBetween( Date from, Date to );


}
