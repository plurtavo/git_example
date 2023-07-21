package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IMonitoringService;
import mx.com.prosa.nabhi.dash.model.SearchConditions;
import mx.com.prosa.nabhi.dash.model.monitoring.InstitutionView;
import mx.com.prosa.nabhi.dash.model.monitoring.MonitoringView;
import mx.com.prosa.nabhi.dash.model.monitoring.TerminalView;
import mx.com.prosa.nabhi.dash.security.UserSession;
import mx.com.prosa.nabhi.misc.domain.personalized.entity.ATDUpTimeEntity;
import mx.com.prosa.nabhi.misc.domain.personalized.repository.ATDUpTimeRepository;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.domain.single.repository.IDFKeyRepository;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.dash.MonitoringException;
import mx.com.prosa.nabhi.misc.exception.dash.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MonitoringService implements IMonitoringService {

    private static final Logger LOGGER = LoggerFactory.getLogger( MonitoringService.class );
    private final ATDUpTimeRepository atdUpTimeRepository;
    private final UserSession userSession;
    private final IDFKeyRepository idfKeyRepository;

    @Autowired
    public MonitoringService( ATDUpTimeRepository atdUpTimeRepository, UserSession userSession, IDFKeyRepository idfKeyRepository ) {
        this.atdUpTimeRepository = atdUpTimeRepository;
        this.userSession = userSession;
        this.idfKeyRepository = idfKeyRepository;
    }

    @Override
    public MonitoringView getReport( SearchConditions searchConditions ) throws MonitoringException {
        IDFEntityKey key = lookupUser( searchConditions );
        log( searchConditions );
        MonitoringView view = new MonitoringView();
        List < InstitutionView > institutionViews = new ArrayList <>();
        view.setInstitutionViews( institutionViews );
        if ( searchConditions.getTerminalId() != null && !searchConditions.getTerminalId().isEmpty() ) {
            TerminalView terminalView = getByTerminalId( searchConditions, Optional.empty(), key );
            InstitutionView institutionView = getInstitutionView( terminalView, searchConditions.getFiid() );
            institutionViews.add( institutionView );
            return view;
        } else if ( searchConditions.getFiid() != null && !searchConditions.getFiid().isEmpty() ) {
            institutionViews.add( getByInstitution( searchConditions, Optional.empty() ) );
            return view;
        } else {
            List < IDFEntityKey > ifs = idfKeyRepository.findAll();
            for ( IDFEntityKey idf : ifs ) {
                try {
                    institutionViews.add( getByInstitution( searchConditions, Optional.of( idf ) ) );
                } catch ( MonitoringException e ) {
                    if ( e.getError() == CatalogError.UPTIME_ERROR_IDF_ATM_EMPTY ) {
                        LOGGER.error( String.format( "%s %s", CatalogError.UPTIME_ERROR_IDF_ATM_EMPTY.getMessage(), idf.getFiid() ) );
                    }
                }
            }
            return view;
        }
    }

    private InstitutionView getByInstitution( SearchConditions searchConditions, Optional < IDFEntityKey > keyOptional ) throws MonitoringException {
        if ( !keyOptional.isPresent() ) {
            keyOptional = idfKeyRepository.findById( searchConditions.getFiid() );
        }
        List < TerminalView > terminalViews = new ArrayList <>();
        if ( keyOptional.isPresent() ) {
            List < ATDUpTimeEntity > atms = atdUpTimeRepository.findAllByIdf( keyOptional.get() );
            if ( !atms.isEmpty() ) {
                searchAllRed( atms, keyOptional.get(), terminalViews, searchConditions );
                return getInstitutionUptime( terminalViews, keyOptional.get().getFiid() );
            } else {
                throw new MonitoringException( CatalogError.UPTIME_ERROR_IDF_ATM_EMPTY );
            }
        } else {
            throw new MonitoringException( CatalogError.UPTIME_ERROR_IDF_NOT_EXIST );
        }
    }

    private void searchAllRed( List < ATDUpTimeEntity > atms, IDFEntityKey key, List < TerminalView > terminalViews, SearchConditions searchConditions ) throws MonitoringException {
        for ( ATDUpTimeEntity atm : atms ) {
            try {
                terminalViews.add( getByTerminalId( searchConditions, Optional.of( atm ), key ) );
            } catch ( MonitoringException e ) {
                if ( e.getError() != CatalogError.UPTIME_ERROR_BY_TERMINAL_NO_RESULTS ) {
                    throw e;
                }
            }
        }
    }

    private InstitutionView getInstitutionView( TerminalView terminalView, String fiid ) {
        List < TerminalView > terminalViews = new ArrayList <>();
        terminalViews.add( terminalView );
        InstitutionView institutionView = new InstitutionView();
        institutionView.setFiid( fiid.equals( "" ) ? "PROS" : fiid );
        institutionView.setTerminalViews( terminalViews );
        return institutionView;
    }

    private TerminalView getByTerminalId( SearchConditions searchConditions, Optional < ATDUpTimeEntity > optional, IDFEntityKey key ) throws MonitoringException {
        if ( !optional.isPresent() ) {
            optional = secureSearchByATM( searchConditions, key );
        }
        if ( optional.isPresent() ) {
            searchConditions.setFiid( optional.get().getIdf().getFiid() );
            return terminalView( optional.get() );
        } else {
            throw new MonitoringException( CatalogError.UPTIME_ERROR_BY_TERMINAL_NO_RESULTS );
        }
    }

    private TerminalView terminalView( ATDUpTimeEntity atdUpTimeEntity ) {
        TerminalView terminalView = new TerminalView();
        terminalView.setTerminalId( atdUpTimeEntity.getTerminalId() );
        terminalView.setLocation( atdUpTimeEntity.getLocation() );
        terminalView.setOnline( atdUpTimeEntity.isOnline() );
        return terminalView;
    }

    private InstitutionView getInstitutionUptime( List < TerminalView > terminalViews, String fiid ) {
        InstitutionView institutionView = new InstitutionView();
        institutionView.setFiid( fiid );
        institutionView.setTerminalViews( terminalViews );
        return institutionView;
    }

    private void log( SearchConditions searchConditions ) {
        if ( searchConditions.getTerminalId() != null && !searchConditions.getTerminalId().isEmpty() ) {
            if ( LOGGER.isInfoEnabled() ) {
                LOGGER.info( String.format( "Buscando monitoreo por el cajero %s", searchConditions.getTerminalId() ) );
            }
        } else if ( searchConditions.getFiid() != null && !searchConditions.getFiid().isEmpty() ) {
            if ( LOGGER.isInfoEnabled() ) {
                LOGGER.info( String.format( "Buscando monitoreo por institución %s", searchConditions.getFiid() ) );
            }
        } else {
            if ( LOGGER.isInfoEnabled() ) {
                LOGGER.info( "Buscando monitoreo por toda la red de cajeros Prosa" );
            }
        }
    }

    private IDFEntityKey lookupUser( SearchConditions conditions ) throws MonitoringException {
        try {
            IDFEntityKey key = userSession.getIDFForCurrentUser();
            if ( !key.getFiid().equals( "PROS" ) ) {
                conditions.setFiid( key.getFiid() );
            }
            return key;
        } catch ( UserNotFoundException e ) {
            throw new MonitoringException( e.getError() );
        }
    }

    private Optional < ATDUpTimeEntity > secureSearchByATM( SearchConditions searchConditions, IDFEntityKey key ) {
        if ( !key.getFiid().equals( "PROS" ) ) {
            return atdUpTimeRepository.findByTerminalIdAndIdf( searchConditions.getTerminalId(), key );
        } else {
            return atdUpTimeRepository.findById( searchConditions.getTerminalId() );
        }
    }
}
