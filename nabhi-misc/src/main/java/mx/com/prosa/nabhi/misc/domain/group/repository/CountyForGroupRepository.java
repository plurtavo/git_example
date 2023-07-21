package mx.com.prosa.nabhi.misc.domain.group.repository;

import mx.com.prosa.nabhi.misc.domain.group.entity.CountyForGroupEntity;
import mx.com.prosa.nabhi.misc.domain.group.entity.StateForGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountyForGroupRepository extends JpaRepository < CountyForGroupEntity, Integer > {

    List < CountyForGroupEntity > findAllByState( StateForGroupEntity state );

}
