package mx.com.prosa.nabhi.misc.domain.personalized.repository;

import mx.com.prosa.nabhi.misc.domain.personalized.entity.ATDTransactionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ATDTransactionalRepository extends JpaRepository < ATDTransactionalEntity, String > {

    Optional < ATDTransactionalEntity > findByIp( String ip );

}
