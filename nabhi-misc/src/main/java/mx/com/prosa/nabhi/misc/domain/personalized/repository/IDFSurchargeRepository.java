package mx.com.prosa.nabhi.misc.domain.personalized.repository;

import mx.com.prosa.nabhi.misc.domain.personalized.entity.IDFSurchargeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDFSurchargeRepository extends JpaRepository < IDFSurchargeEntity, String > {

}
