package mx.com.prosa.nabhi.misc.domain.group.repository;

import mx.com.prosa.nabhi.misc.domain.group.entity.GroupEntity;
import mx.com.prosa.nabhi.misc.domain.group.entity.IDFForGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository < GroupEntity, Integer > {


    Optional< GroupEntity > findByName( String name );

    Optional< GroupEntity > findByNameAndIdf( String name, IDFForGroupEntity idf );

    List < GroupEntity > findAllByIdf( IDFForGroupEntity idf );

}
