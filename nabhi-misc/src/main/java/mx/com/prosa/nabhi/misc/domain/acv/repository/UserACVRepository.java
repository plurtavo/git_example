package mx.com.prosa.nabhi.misc.domain.acv.repository;

import mx.com.prosa.nabhi.misc.domain.acv.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserACVRepository extends JpaRepository < UserEntity, String > {


}