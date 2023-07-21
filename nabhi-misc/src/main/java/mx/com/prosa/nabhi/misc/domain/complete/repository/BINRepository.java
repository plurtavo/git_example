package mx.com.prosa.nabhi.misc.domain.complete.repository;

import mx.com.prosa.nabhi.misc.domain.complete.entity.BINEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BINRepository extends JpaRepository < BINEntity, Integer > {

    Optional < BINEntity > findByBinAndBinLenAndPanLen( String bin, int binLen, int panLen );

    List < BINEntity > findAllByFiidOrderByBin( String fiid );

}
