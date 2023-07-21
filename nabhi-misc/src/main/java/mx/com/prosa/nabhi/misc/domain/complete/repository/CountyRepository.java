package mx.com.prosa.nabhi.misc.domain.complete.repository;

import mx.com.prosa.nabhi.misc.domain.complete.entity.CountyEntity;
import mx.com.prosa.nabhi.misc.domain.complete.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountyRepository extends JpaRepository < CountyEntity, Integer > {
    //CountyEntity findByCountyNameAndState( String countyName, StateEntity state );
    List < CountyEntity > findByState( StateEntity stateEntity );
}
