package mx.com.prosa.nabhi.misc.domain.log.repository;

import mx.com.prosa.nabhi.misc.domain.log.entity.LogEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LogEventRepository extends JpaRepository < LogEventEntity, Integer > {

    //Cambio release/eventos
    List < LogEventEntity > findByUserNameOrderByTimeDesc( String userName );

    List < LogEventEntity > findByTimeBetweenOrderByTimeDesc( Date from, Date to );

    List < LogEventEntity > findByTimeBetweenAndUserNameOrderByTimeDesc( Date from, Date to, String userName );
    //Cambio release/eventos

}
