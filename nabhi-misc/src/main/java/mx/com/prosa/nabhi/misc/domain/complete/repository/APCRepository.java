package mx.com.prosa.nabhi.misc.domain.complete.repository;

import mx.com.prosa.nabhi.misc.domain.complete.entity.APCEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface APCRepository extends JpaRepository < APCEntity, Integer > {

    Optional < APCEntity > findByFiidAndTranCodeAndFormAcctAndToAcct( String fiid, String tranCode, String fromAccount, String toAccount );

    List < APCEntity > findAllByFiid( String fiid );
}
