package mx.com.prosa.nabhi.misc.domain.complete.repository;

import mx.com.prosa.nabhi.misc.domain.complete.entity.ComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends JpaRepository < ComponentEntity, String > {

}
