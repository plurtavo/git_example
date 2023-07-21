package mx.com.prosa.nabhi.misc.domain.complete.repository;

import mx.com.prosa.nabhi.misc.domain.complete.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository < StateEntity, String > {
    StateEntity findByStateName( String stateName );
}
