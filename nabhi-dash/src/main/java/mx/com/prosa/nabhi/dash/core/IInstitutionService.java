package mx.com.prosa.nabhi.dash.core;

import mx.com.prosa.nabhi.misc.domain.complete.entity.APCEntity;
import mx.com.prosa.nabhi.misc.domain.complete.entity.BINEntity;
import mx.com.prosa.nabhi.misc.domain.complete.entity.ScreenGroupEntity;
import mx.com.prosa.nabhi.misc.domain.complete.entity.SurchargeEntity;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;

import java.util.List;

public interface IInstitutionService {


    void update( String fiid, SurchargeEntity... entities ) throws IDFException;

    void delete( String fiid, SurchargeEntity... entities ) throws IDFException;

    List < SurchargeEntity > findSurcharges( String fiid ) throws IDFException, DataBaseException;

    IDFEntityKey lookupUser() throws IDFException;


}
