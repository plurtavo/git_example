package mx.com.prosa.nabhi.dash.core.impl;

import mx.com.prosa.nabhi.dash.core.IInstitutionService;
import mx.com.prosa.nabhi.dash.security.UserSession;
import mx.com.prosa.nabhi.misc.domain.complete.entity.SurchargeEntity;
import mx.com.prosa.nabhi.misc.domain.personalized.entity.IDFSurchargeEntity;
import mx.com.prosa.nabhi.misc.domain.personalized.repository.IDFSurchargeRepository;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.dash.UserNotFoundException;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InstitutionService implements IInstitutionService {

    private static final Logger LOGGER = LoggerFactory.getLogger( InstitutionService.class );

    private final IDFSurchargeRepository idfSurchargeRepository;
    private final UserSession userSession;

    @Autowired
    public InstitutionService( IDFSurchargeRepository idfSurchargeRepository, UserSession userSession ) {
        this.idfSurchargeRepository = idfSurchargeRepository;
        this.userSession = userSession;
    }

    @Override
    public void update( String fiid, SurchargeEntity... entities ) throws IDFException {
        Optional < IDFSurchargeEntity > optional = idfSurchargeRepository.findById( fiid );
        if ( optional.isPresent() ) {
            optional.get().getSurcharges().addAll( Arrays.asList( entities ) );
            idfSurchargeRepository.save( optional.get() );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( "Comisión agregada para el FIID: %s", fiid ) );
            }
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( String.format( "No se pudo guardar la comisión %s %s", CatalogError.IDF_NOT_FOUND.getMessage(), fiid ) );
            }
            throw new IDFException( CatalogError.IDF_NOT_FOUND );
        }
    }

    @Override
    public void delete( String fiid, SurchargeEntity... entities ) throws IDFException {
        Optional < IDFSurchargeEntity > optional = idfSurchargeRepository.findById( fiid );
        if ( optional.isPresent() ) {
            IDFSurchargeEntity idf = optional.get();
            Set < SurchargeEntity > newEntities = new HashSet <>();
            for ( SurchargeEntity entity : entities ) {
                for ( SurchargeEntity oldEntity : idf.getSurcharges() ) {
                    if ( oldEntity.hashCode() != entity.hashCode() ) {
                        newEntities.add( oldEntity );
                    }
                }
            }
            idf.setSurcharges( newEntities );
            idfSurchargeRepository.save( idf );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( "Comisión elminiada para el FIID: %s", fiid ) );
            }
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( String.format( "No se pudo eliminar la comisión %s %s", CatalogError.IDF_NOT_FOUND.getMessage(), fiid ) );
            }
            throw new IDFException( CatalogError.IDF_NOT_FOUND );
        }
    }

    @Override
    public List < SurchargeEntity > findSurcharges( String fiid ) throws IDFException, DataBaseException {
        Optional < IDFSurchargeEntity > optional = idfSurchargeRepository.findById( fiid );
        if ( optional.isPresent() ) {
            if ( !optional.get().getSurcharges().isEmpty() ) {
                return new ArrayList <>( optional.get().getSurcharges() );
            } else {
                if ( LOGGER.isErrorEnabled() ) {
                    LOGGER.error( CatalogError.FIND_ERROR_LIST_NOT_FOUND.toString(), fiid );
                }
                throw new DataBaseException( CatalogError.FIND_ERROR_LIST_NOT_FOUND );
            }
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( String.format( "COMISIÓN vació %s %s", CatalogError.IDF_NOT_FOUND.getMessage(), fiid ) );
            }
            throw new IDFException( CatalogError.IDF_NOT_FOUND );
        }
    }


    @Override
    public IDFEntityKey lookupUser() throws IDFException {
        try {
            return userSession.getIDFForCurrentUser();
        } catch ( UserNotFoundException e ) {
            throw new IDFException( e.getError() );
        }
    }
}
