package mx.com.prosa.nabhi.misc.domain.security.repository;

import mx.com.prosa.nabhi.misc.domain.security.entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository < PrivilegeEntity, String > {
}
