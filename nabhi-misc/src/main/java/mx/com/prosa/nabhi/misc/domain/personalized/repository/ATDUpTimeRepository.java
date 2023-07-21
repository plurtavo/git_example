package mx.com.prosa.nabhi.misc.domain.personalized.repository;

//Cambio release/monitoreoatm

import mx.com.prosa.nabhi.misc.domain.personalized.entity.ATDUpTimeEntity;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//Cambio release/monitoreoatm

@Repository
public interface ATDUpTimeRepository extends JpaRepository < ATDUpTimeEntity, String > {

    //Cambio release/monitoreoatm
    List< ATDUpTimeEntity > findAllByIdf( IDFEntityKey idf );

    Optional < ATDUpTimeEntity > findByTerminalIdAndIdf( String s, IDFEntityKey idf );
    //Cambio release/monitoreoatm
}
