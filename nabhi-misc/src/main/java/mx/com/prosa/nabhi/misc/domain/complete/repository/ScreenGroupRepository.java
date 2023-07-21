package mx.com.prosa.nabhi.misc.domain.complete.repository;

import mx.com.prosa.nabhi.misc.domain.complete.entity.ScreenGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScreenGroupRepository extends JpaRepository < ScreenGroupEntity, String > {

    List < ScreenGroupEntity > findAllByFiid( String fiid );

    Optional < ScreenGroupEntity > findByFiidAndGroupId( String fiid, String id );
}
