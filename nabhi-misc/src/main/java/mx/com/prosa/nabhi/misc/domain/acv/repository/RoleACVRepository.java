package mx.com.prosa.nabhi.misc.domain.acv.repository;

import mx.com.prosa.nabhi.misc.domain.acv.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleACVRepository extends JpaRepository < RoleEntity, String > {


}
