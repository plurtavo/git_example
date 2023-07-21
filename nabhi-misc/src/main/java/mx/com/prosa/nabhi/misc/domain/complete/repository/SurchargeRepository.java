package mx.com.prosa.nabhi.misc.domain.complete.repository;

import mx.com.prosa.nabhi.misc.domain.complete.entity.SurchargeEntity;
import mx.com.prosa.nabhi.misc.domain.complete.entity.composite.SurchargeIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurchargeRepository extends JpaRepository < SurchargeEntity, SurchargeIdentity > {

}
