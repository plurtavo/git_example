package mx.com.prosa.nabhi.misc.domain.personalized.repository;

import mx.com.prosa.nabhi.misc.domain.personalized.entity.ATDScreenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ATDScreenRepository extends JpaRepository < ATDScreenEntity, String > {

    Optional < ATDScreenEntity > findByIp( String ip );

}
