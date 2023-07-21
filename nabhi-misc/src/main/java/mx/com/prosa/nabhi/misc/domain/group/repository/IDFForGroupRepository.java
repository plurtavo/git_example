package mx.com.prosa.nabhi.misc.domain.group.repository;

import mx.com.prosa.nabhi.misc.domain.group.entity.IDFForGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDFForGroupRepository extends JpaRepository < IDFForGroupEntity, String > {

}
