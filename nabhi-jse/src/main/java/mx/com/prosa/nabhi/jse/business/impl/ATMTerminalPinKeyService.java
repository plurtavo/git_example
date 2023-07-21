package mx.com.prosa.nabhi.jse.business.impl;

import mx.com.prosa.nabhi.jse.business.ITPKExchangeService;
import mx.com.prosa.nabhi.jse.core.database.memory.atm.ATMSearch;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.rest.RestExchangeException;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDBasic;
import mx.com.prosa.nabhi.misc.model.jke.KeyRequest;
import mx.com.prosa.nabhi.misc.model.jke.TmkEntity;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import mx.com.prosa.nabhi.misc.rest.jke.JKERequester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class ATMTerminalPinKeyService implements ITPKExchangeService {

    private static final Logger LOG = LoggerFactory.getLogger( ATMTerminalPinKeyService.class );
    private final JKERequester jkeRequester;
    private final ATMSearch atmSearch;

    @Autowired
    public ATMTerminalPinKeyService( JKERequester jkeRequester, ATMSearch atmSearch ) {
        this.jkeRequester = jkeRequester;
        this.atmSearch = atmSearch;
    }

    @Override
    public TmkEntity exchange( Generic atmIp ) throws ATMException {
        try {
            ATDBasic atd;
            atd = atmSearch.searchBasic( atmIp.getTermId() );
            KeyRequest tpk = new KeyRequest();
            tpk.setTermType( atd.getDeviceType() );
            tpk.setAtmRemote( atd.getTerminalId() );
            TmkEntity tmkEntity = new TmkEntity();
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "Realizando solicitud de llave TPK para el cajero %s", atmIp.getTermId() ) );
            }
            tmkEntity.setMode( jkeRequester.exchangeTPK( tpk, "/exchange/{nodeName}", atd.getNodeX().getNodeName() ) );
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( "Solicitud de TPK exitosa" );
            }
            tmkEntity.setError( String.valueOf( new Timestamp( System.currentTimeMillis() ).getTime() / 1000L ) );
            tmkEntity.setSequence( atd.getSequence() );
            return tmkEntity;
        } catch ( RestExchangeException e ) {
            throw new ATMException( CatalogError.ATM_NO_EXCHANGE_KEY, "Causa: " + e.getMessage() );
        }
    }
}
