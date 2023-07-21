package mx.com.prosa.nabhi.misc.domain.personalized.repository;

import mx.com.prosa.nabhi.misc.domain.personalized.entity.IDFDashEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDFDashRepository extends JpaRepository < IDFDashEntity, String > {

}
