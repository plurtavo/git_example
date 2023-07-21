package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.dash.model.group.CriteriaSearch;
import mx.com.prosa.nabhi.misc.domain.group.dto.*;
import mx.com.prosa.nabhi.misc.exception.dash.GroupException;

import java.util.List;

public interface IGroupService {

    String create( Group group ) throws GroupException;

    String update( Group group ) throws GroupException;

    String delete( Group group ) throws GroupException;

    Group findByName( String name ) throws GroupException;

    List< Group > findAll(  ) throws GroupException;

    List< String > findAllOnlyNames(  ) throws GroupException;

    List < IDF > findIDFS() throws GroupException;

    List < State > findStates() throws GroupException;

    List < County > findCountiesByState( State state ) throws GroupException;

    List < ATD > findByCriteria( CriteriaSearch criteriaSearch ) throws GroupException;

    List< String > findATDSNames( String name ) throws GroupException;


}
