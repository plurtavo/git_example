package mx.com.prosa.nabhi.misc.domain.redcat.repository;

import mx.com.prosa.nabhi.misc.domain.redcat.entity.ATDForRedcatEntity;
import mx.com.prosa.nabhi.misc.domain.redcat.entity.IDFForRedcatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ATDForRedcatRepository extends JpaRepository<ATDForRedcatEntity, String> {

    List < ATDForRedcatEntity > findAllByIdf( IDFForRedcatEntity idf );

}
