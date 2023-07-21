package mx.com.prosa.nabhi.misc.domain.personalized.repository;

import mx.com.prosa.nabhi.misc.domain.complete.entity.PackageEntity;
import mx.com.prosa.nabhi.misc.domain.personalized.entity.ATDDashEntity;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ATDDashRepository extends JpaRepository < ATDDashEntity, String > {

    Optional < ATDDashEntity > findByIp( String ip );

    Optional < ATDDashEntity > findByTerminalIdAndIdfEquals( String terminalId, IDFEntityKey idf );

    List < ATDDashEntity > findAllByIdfEquals( IDFEntityKey idfEntity );

    List < ATDDashEntity > findAllByPackageNameEqualsAndIdfEquals( PackageEntity packageEntity, IDFEntityKey idfEntity );

}
