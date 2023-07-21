package mx.com.prosa.nabhi.misc.domain.single.repository;

import mx.com.prosa.nabhi.misc.domain.single.entity.UserSingleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSingleRepository extends JpaRepository < UserSingleEntity, String > {


}
