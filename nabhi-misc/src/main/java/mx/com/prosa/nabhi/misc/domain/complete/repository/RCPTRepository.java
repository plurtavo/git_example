package mx.com.prosa.nabhi.misc.domain.complete.repository;

import mx.com.prosa.nabhi.misc.domain.complete.entity.RCPTEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RCPTRepository extends JpaRepository < RCPTEntity, Integer > {

    List< RCPTEntity > findAllByFiid( String fiid );


    Optional < RCPTEntity > findByName( String name );

}
