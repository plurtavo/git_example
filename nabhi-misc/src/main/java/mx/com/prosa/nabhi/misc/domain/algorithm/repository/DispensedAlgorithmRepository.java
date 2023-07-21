package mx.com.prosa.nabhi.misc.domain.algorithm.repository;

import mx.com.prosa.nabhi.misc.domain.algorithm.entity.DispensedAlgorithmEntity;
import mx.com.prosa.nabhi.misc.domain.algorithm.entity.IDFForAlgorithmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DispensedAlgorithmRepository extends JpaRepository < DispensedAlgorithmEntity, Integer > {

    Optional < DispensedAlgorithmEntity > findByName( String name );

    Optional < DispensedAlgorithmEntity > findByNameAndIdf( String name, IDFForAlgorithmEntity idf );

    List < DispensedAlgorithmEntity > findAllByIdf( IDFForAlgorithmEntity idf );

    List < DispensedAlgorithmEntity > findAllByDispensedTypeIsNot( String thresholds );


}
