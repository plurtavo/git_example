package mx.com.prosa.nabhi.misc.domain.personalized.repository;

import mx.com.prosa.nabhi.misc.domain.personalized.entity.ATDBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ATDBasicRepository extends JpaRepository < ATDBasicEntity, String > {

    Optional < ATDBasicEntity > findByIp( String ip );

}
