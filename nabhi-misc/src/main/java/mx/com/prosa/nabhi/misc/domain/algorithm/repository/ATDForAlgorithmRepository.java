package mx.com.prosa.nabhi.misc.domain.algorithm.repository;

import mx.com.prosa.nabhi.misc.domain.algorithm.entity.ATDForAlgorithmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ATDForAlgorithmRepository extends JpaRepository < ATDForAlgorithmEntity, String > {


}
