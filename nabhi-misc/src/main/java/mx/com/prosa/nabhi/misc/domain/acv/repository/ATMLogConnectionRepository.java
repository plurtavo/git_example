package mx.com.prosa.nabhi.misc.domain.acv.repository;

import mx.com.prosa.nabhi.misc.domain.acv.entity.ATDForLogEntity;
import mx.com.prosa.nabhi.misc.domain.acv.entity.ATMLogConectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ATMLogConnectionRepository extends JpaRepository < ATMLogConectionEntity, Integer > {

    List < ATMLogConectionEntity > findAllByAtd( ATDForLogEntity entity );
    List < ATMLogConectionEntity > findAllByAtdOrderByIdAsc( ATDForLogEntity entity );

    Optional< ATMLogConectionEntity > findAllBySessionId( String sessionId );


}
