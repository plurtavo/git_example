package mx.com.prosa.nabhi.misc.domain.security.repository;

import mx.com.prosa.nabhi.misc.domain.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository < UserEntity, String > {


}
