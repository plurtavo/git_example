package mx.com.prosa.nabhi.dash.temp.old.repository;

import mx.com.prosa.nabhi.dash.temp.old.entity.ATDOldCassetteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ATDOldCassetteRepository extends JpaRepository< ATDOldCassetteEntity, String> {
}
