package mx.com.prosa.nabhi.misc.domain.complete.repository;

import mx.com.prosa.nabhi.misc.domain.complete.entity.ATDEntity;
import mx.com.prosa.nabhi.misc.domain.complete.entity.PackageEntity;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ATDRepository extends JpaRepository < ATDEntity, String > {

    Optional < ATDEntity > findByTerminalIdAndIdfEquals( String terminalId, IDFEntityKey idf );

    Optional < ATDEntity > findByIp( String ip );

    List < ATDEntity > findAllByIdfEquals( IDFEntityKey idfEntity );
    List < ATDEntity > findAllByPackageNameEqualsAndIdfEquals( PackageEntity packageEntity, IDFEntityKey idfEntity );


}
