package mx.com.prosa.nabhi.misc.domain.group.repository;

import mx.com.prosa.nabhi.misc.domain.group.entity.ATDForGroupEntity;
import mx.com.prosa.nabhi.misc.domain.group.entity.IDFForGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ATDForGroupRepository extends JpaRepository < ATDForGroupEntity, String >, JpaSpecificationExecutor < ATDForGroupEntity > {

    Optional< ATDForGroupEntity > findByTerminalIdAndIdf( String terminalId, IDFForGroupEntity idf );

}
