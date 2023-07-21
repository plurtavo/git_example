package mx.com.prosa.nabhi.misc.domain.complete.repository;

import mx.com.prosa.nabhi.misc.domain.complete.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository < ImageEntity, String > {

    Optional < ImageEntity > findByNameAndCategory( String name, String category );

    List< ImageEntity > findAllByFiid( String fiid );

}
