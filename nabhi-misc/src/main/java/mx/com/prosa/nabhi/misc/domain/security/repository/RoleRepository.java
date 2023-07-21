package mx.com.prosa.nabhi.misc.domain.security.repository;

import mx.com.prosa.nabhi.misc.domain.security.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository < RoleEntity, String > {


}
