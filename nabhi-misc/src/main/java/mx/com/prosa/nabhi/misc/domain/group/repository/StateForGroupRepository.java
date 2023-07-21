package mx.com.prosa.nabhi.misc.domain.group.repository;

import mx.com.prosa.nabhi.misc.domain.group.entity.StateForGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateForGroupRepository extends JpaRepository < StateForGroupEntity, String > {

}
