package mx.com.prosa.nabhi.misc.domain.redcat.repository;


import mx.com.prosa.nabhi.misc.domain.redcat.entity.RedcatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RedcatRepository extends JpaRepository<RedcatEntity, String > {

    public List< RedcatEntity > findAllByFiid( String fiid );

    public List < RedcatEntity > findByStartDateAndEndDateAndTermIdOrderById(  Date form, Date to, String termId );

    public List < RedcatEntity > findByStartDateBetweenAndTermId(  Date form, Date to, String termId );

    public List < RedcatEntity > findByStartDateBetweenAndTermIdOrderByStartDate(  Date form, Date to, String termId );

    public List < RedcatEntity > findByFiidAndStartDateIsBetween ( String fii, Date form, Date to );

    public List < RedcatEntity > findByTermIdAndStartDateIsBetween( String termId, Date form, Date to );

    public List < RedcatEntity > findByStartDateAndEndDateAndFiidOrderById ( Date from, Date to, String fiid );
}
