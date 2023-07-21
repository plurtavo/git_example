package mx.com.prosa.nabhi.misc.domain.node.repository;

import mx.com.prosa.nabhi.misc.domain.node.entity.NodeTCPEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeTCPRepository extends JpaRepository < NodeTCPEntity, String > {

    List< NodeTCPEntity > findByNodeType( String nodeType );

}
