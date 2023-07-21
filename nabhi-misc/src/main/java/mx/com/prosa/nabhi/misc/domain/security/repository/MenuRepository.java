package mx.com.prosa.nabhi.misc.domain.security.repository;

import mx.com.prosa.nabhi.misc.domain.security.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository < MenuEntity, Integer > {

    Optional< MenuEntity > findByMenu( String menu );
}
