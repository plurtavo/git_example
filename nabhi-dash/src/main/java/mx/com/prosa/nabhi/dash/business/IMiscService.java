package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.model.jdb.Country;
import mx.com.prosa.nabhi.misc.model.jdb.County;
import mx.com.prosa.nabhi.misc.model.jdb.State;
import mx.com.prosa.nabhi.misc.model.jdb.TranAllowed;

import java.util.List;

public interface IMiscService {


    Country findCountry( String countryCode ) throws DataBaseException;

    County findCounty( int countyCode ) throws DataBaseException;

    State findState( String stateCode ) throws DataBaseException;

    TranAllowed findTranAllow( String code ) throws DataBaseException;

    List < String > findKeysState() throws DataBaseException;

    List < String > findKeysCounty() throws DataBaseException;

    List < String > findKeysPackage() throws DataBaseException;

    List < String > findKeysTranAllow() throws DataBaseException;

    String licence( String fiid, String licence ) throws DataBaseException;

    String licence( String fiid, String licence, String atm ) throws DataBaseException;


}
