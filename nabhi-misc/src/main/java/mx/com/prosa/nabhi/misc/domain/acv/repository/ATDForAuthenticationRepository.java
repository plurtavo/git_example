package mx.com.prosa.nabhi.misc.domain.acv.repository;

import mx.com.prosa.nabhi.misc.domain.acv.entity.ATDForAuthenticationEntity;
import mx.com.prosa.nabhi.misc.domain.acv.entity.PayloadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ATDForAuthenticationRepository extends JpaRepository < ATDForAuthenticationEntity, String > {

    List < ATDForAuthenticationEntity > findAllByScreenGroup( PayloadEntity entity );

}
