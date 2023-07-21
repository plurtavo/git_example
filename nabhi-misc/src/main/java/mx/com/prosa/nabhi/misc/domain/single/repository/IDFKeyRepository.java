package mx.com.prosa.nabhi.misc.domain.single.repository;

import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDFKeyRepository extends JpaRepository < IDFEntityKey, String > {

}
