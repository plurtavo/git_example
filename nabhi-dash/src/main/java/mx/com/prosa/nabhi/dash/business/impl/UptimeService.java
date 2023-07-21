package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IUptimeService;
import mx.com.prosa.nabhi.dash.model.uptime.*;
import mx.com.prosa.nabhi.dash.report.*;
import mx.com.prosa.nabhi.dash.security.UserSession;
import mx.com.prosa.nabhi.misc.domain.complete.entity.UpTimeEntity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.UpTimeRepository;
import mx.com.prosa.nabhi.misc.domain.personalized.entity.ATDUpTimeEntity;
import mx.com.prosa.nabhi.misc.domain.personalized.repository.ATDUpTimeRepository;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.domain.single.repository.IDFKeyRepository;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.dash.UptimeException;
import mx.com.prosa.nabhi.misc.exception.dash.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UptimeService implements IUptimeService {

    private static final Logger LOGGER = LoggerFactory.getLogger( UptimeService.class );
    private final UpTimeRepository upTimeRepository;
    private final ATDUpTimeRepository atdUpTimeRepository;
    private final IDFKeyRepository idfKeyRepository;
    private final ExcelReportBuilder excelReportBuilder;
    private final UserSession userSession;
    private final SimpleDateFormat formatter = new SimpleDateFormat( "dd/MM/yyyy" );

    @Autowired
    public UptimeService( UpTimeRepository upTimeRepository, ATDUpTimeRepository atdUpTimeRepository, IDFKeyRepository idfKeyRepository, ExcelReportBuilder excelReportBuilder, UserSession userSession ) {
        this.upTimeRepository = upTimeRepository;
        this.atdUpTimeRepository = atdUpTimeRepository;
        this.idfKeyRepository = idfKeyRepository;
        this.excelReportBuilder = excelReportBuilder;
        this.userSession = userSession;
    }

    @Override
    public UptimeReport getReportRaw( ReportSearchConditions reportSearchConditions ) throws UptimeException {
        IDFEntityKey key = lookupUser( reportSearchConditions );
        log( reportSearchConditions );
        Pageable pageable = getPageableRequest( reportSearchConditions.getPage(), reportSearchConditions.getResults() );
        List < InstitutionUptime > institutionUptimes = new ArrayList <>();
        UptimeReport report = new UptimeReport();
        report.setInstitutionUptimes( institutionUptimes );
        if ( reportSearchConditions.getTerminalId() != null && !reportSearchConditions.getTerminalId().isEmpty() ) {
            TerminalUptime uptime = getByTerminalId( reportSearchConditions, pageable, Optional.empty(), key );
            institutionUptimes.add( getInstitutionUptime( uptime, reportSearchConditions.getFiid() ) );
            return report;
        } else if ( reportSearchConditions.getFiid() != null && !reportSearchConditions.getFiid().isEmpty() ) {
            institutionUptimes.add( getByInstitution( reportSearchConditions, pageable, Optional.empty() ) );
            return report;
        } else {
            List < IDFEntityKey > ifs = idfKeyRepository.findAll();
            for ( IDFEntityKey idf : ifs ) {
                try {
                    institutionUptimes.add( getByInstitution( reportSearchConditions, pageable, Optional.of( idf ) ) );
                } catch ( UptimeException e ) {
                    if ( e.getError() == CatalogError.UPTIME_ERROR_IDF_ATM_EMPTY ) {
                        LOGGER.error( String.format( "%s %s", CatalogError.UPTIME_ERROR_IDF_ATM_EMPTY.getMessage(), idf.getFiid() ) );
                    }
                }
            }
            return report;
        }
    }

    @Override
    public ByteArrayOutputStream getReportRawExcel( ReportSearchConditions reportSearchConditions ) throws UptimeException {
        return excelReportBuilder.buildRawUptime( getReportRaw( reportSearchConditions ).getInstitutionUptimes().get( 0 ).getTerminalUptimes().get( 0 ) );
    }

    @Override
    public ByteArrayOutputStream getReportExcel( ReportSearchConditions reportSearchConditions ) throws UptimeException {
        return excelReportBuilder.buildBookUptime( getReport( reportSearchConditions ) );
    }

    @Override
    public BookJsonUptime getReport( ReportSearchConditions reportSearchConditions ) throws UptimeException {
        UptimeReport uptimeReport = getReportRaw( reportSearchConditions );
        BookJsonUptime bookJsonUptime = new BookJsonUptime();
        List < SheetJsonUptime > sheetJsonUptimes = new ArrayList <>();
        bookJsonUptime.setSheetJsonUptimes( sheetJsonUptimes );
        for ( InstitutionUptime institutionUptime : uptimeReport.getInstitutionUptimes() ) {
            SheetJsonUptime sheetJsonUptime = new SheetJsonUptime();
            sheetJsonUptime.setSheetName( institutionUptime.getFiid() );
            List < RowJsonUptime > rowJsonUptimes = new ArrayList <>();
            sheetJsonUptime.setRowJsonUptimes( rowJsonUptimes );
            for ( TerminalUptime terminalUptime : institutionUptime.getTerminalUptimes() ) {
                RowJsonUptime rowJsonUptime = new RowJsonUptime();
                rowJsonUptime.setTerminalId( terminalUptime.getTerminalId() );
                rowJsonUptime.setLocation( terminalUptime.getLocation() );
                if ( !terminalUptime.getUptimes().isEmpty() ) {
                    rowJsonUptime.setFrom( terminalUptime.getUptimes().get( terminalUptime.getUptimes().size() - 1 ).getDate() );
                    rowJsonUptime.setTo( terminalUptime.getUptimes().get( 0 ).getDate() );
                } else {
                    rowJsonUptime.setFrom( reportSearchConditions.getForm() );
                    rowJsonUptime.setTo( reportSearchConditions.getTo() );
                }
                rowJsonUptime.setDays( terminalUptime.getUptimes().size() );
                long lup = 0L;
                long max = 86400L * terminalUptime.getUptimes().size();
                for ( SingleUptime singleUptime : terminalUptime.getUptimes() ) {
                    lup += singleUptime.getUptime();
                }
                rowJsonUptime.setUptime( ( lup * 100f ) / max );
                rowJsonUptimes.add( rowJsonUptime );
            }
            sheetJsonUptimes.add( sheetJsonUptime );
        }
        buildSummaries( bookJsonUptime );
        return bookJsonUptime;
    }

    private void buildSummaries( BookJsonUptime bookJsonUptime ) {
        Summary summary = new Summary();
        List < InstitutionSummary > institutionSummaries = new ArrayList <>();
        summary.setInstitutionSummaries( institutionSummaries );
        double summaryUptime = 0f;
        for ( SheetJsonUptime sheet : bookJsonUptime.getSheetJsonUptimes() ) {
            String fiid = sheet.getSheetName();
            double uptime = 0f;
            for ( RowJsonUptime row : sheet.getRowJsonUptimes() ) {
                uptime += row.getUptime();
            }
            InstitutionSummary institutionSummary = new InstitutionSummary();
            institutionSummary.setFiid( fiid );
            institutionSummaries.add( institutionSummary );
            uptime = ( uptime / sheet.getRowJsonUptimes().size() );
            institutionSummary.setUptime( uptime );
            summaryUptime += uptime;
            sheet.setUptime( uptime );
        }
        summary.setUptime( ( summaryUptime / bookJsonUptime.getSheetJsonUptimes().size() ) );
        bookJsonUptime.setSummary( summary );
    }

    private InstitutionUptime getByInstitution( ReportSearchConditions reportSearchConditions, Pageable pageable, Optional < IDFEntityKey > keyOptional ) throws UptimeException {
        if ( !keyOptional.isPresent() ) {
            keyOptional = idfKeyRepository.findById( reportSearchConditions.getFiid() );
        }
        List < TerminalUptime > terminalUptimes = new ArrayList <>();
        if ( keyOptional.isPresent() ) {
            List < ATDUpTimeEntity > atms = atdUpTimeRepository.findAllByIdf( keyOptional.get() );
            if ( !atms.isEmpty() ) {
                searchAllRed( atms, keyOptional.get(), terminalUptimes, reportSearchConditions, pageable );
                return getInstitutionUptime( terminalUptimes, keyOptional.get().getFiid() );
            } else {
                throw new UptimeException( CatalogError.UPTIME_ERROR_IDF_ATM_EMPTY );
            }
        } else {
            throw new UptimeException( CatalogError.UPTIME_ERROR_IDF_NOT_EXIST );
        }
    }

    private void searchAllRed( List < ATDUpTimeEntity > atms, IDFEntityKey key, List < TerminalUptime > terminalUptimes, ReportSearchConditions reportSearchConditions, Pageable pageable ) throws UptimeException {
        for ( ATDUpTimeEntity atm : atms ) {
            try {
                terminalUptimes.add( getByTerminalId( reportSearchConditions, pageable, Optional.of( atm ), key ) );
            } catch ( UptimeException e ) {
                if ( e.getError() != CatalogError.UPTIME_ERROR_BY_TERMINAL_NO_RESULTS ) {
                    throw e;
                }
            }
        }
    }

    private TerminalUptime getByTerminalId( ReportSearchConditions reportSearchConditions, Pageable pageable, Optional < ATDUpTimeEntity > optional, IDFEntityKey key ) throws UptimeException {
        if ( reportSearchConditions.getForm() != null && reportSearchConditions.getTo() != null ) {
            if ( !optional.isPresent() ) {
                optional = secureSearchByATM( reportSearchConditions, key );
            }
            if ( optional.isPresent() ) {
                reportSearchConditions.setTerminalId( optional.get().getTerminalId() );
                List < UpTimeEntity > entities = executeSearchConditions( reportSearchConditions, pageable );
                if ( !entities.isEmpty() ) {
                    reportSearchConditions.setFiid( optional.get().getIdf().getFiid() );
                    return getTerminalUpTime( optional.get(), entities );
                } else {
                    throw new UptimeException( CatalogError.UPTIME_ERROR_BY_TERMINAL_NO_RESULTS );
                }
            } else {
                throw new UptimeException( CatalogError.UPTIME_ERROR_ATM_NOT_EXIST );
            }
        } else {
            throw new UptimeException( CatalogError.UPTIME_ERROR_DATES_EMPTY );
        }
    }

    private InstitutionUptime getInstitutionUptime( TerminalUptime terminalUptime, String fiid ) {
        List < TerminalUptime > terminalUptimes = new ArrayList <>();
        terminalUptimes.add( terminalUptime );
        InstitutionUptime institutionUptime = new InstitutionUptime();
        institutionUptime.setFiid( fiid.equals( "" ) ? "PROS" : fiid );
        institutionUptime.setTerminalUptimes( terminalUptimes );
        return institutionUptime;
    }

    private InstitutionUptime getInstitutionUptime( List < TerminalUptime > terminalUptimes, String fiid ) {
        InstitutionUptime institutionUptime = new InstitutionUptime();
        institutionUptime.setFiid( fiid );
        institutionUptime.setTerminalUptimes( terminalUptimes );
        return institutionUptime;
    }

    private TerminalUptime getTerminalUpTime( ATDUpTimeEntity atdUpTimeEntity, List < UpTimeEntity > upTimeEntities ) {
        TerminalUptime terminalUptime = new TerminalUptime();
        terminalUptime.setTerminalId( atdUpTimeEntity.getTerminalId() );
        terminalUptime.setLocation( atdUpTimeEntity.getLocation() + ", " + atdUpTimeEntity.getCounty().getCountyName() + ", " + atdUpTimeEntity.getCounty().getState().getStateName() );
        List < SingleUptime > uptimes = new ArrayList <>();
        terminalUptime.setUptimes( uptimes );
        for ( UpTimeEntity upTimeEntity : upTimeEntities ) {
            uptimes.add( new SingleUptime( upTimeEntity.getUpTimeIdentity().getDate().toString(), upTimeEntity.getUpTime() ) );
        }
        return terminalUptime;
    }

    private List < UpTimeEntity > executeSearchConditions( ReportSearchConditions sc, Pageable pageable ) throws UptimeException {
        try {
            Date from = formatter.parse( sc.getForm() );
            Date to = formatter.parse( sc.getTo() );
            List < UpTimeEntity > entities;
            if ( sc.getGreaterThan() > 0 ) {
                int greater = getPercentageOfAvailability( sc.getGreaterThan() );
                entities = upTimeRepository.findByUpTimeIdTerminalIdAndUpTimeIdDateBetweenAndUpTimeIsGreaterThanOrderByUpTimeIdDateDesc( sc.getTerminalId(), from, to, greater, pageable );
            } else if ( sc.getLessThan() > 0 ) {
                int less = getPercentageOfAvailability( sc.getLessThan() );
                entities = upTimeRepository.findByUpTimeIdTerminalIdAndUpTimeIdDateBetweenAndUpTimeIsLessThanOrderByUpTimeIdDateDesc( sc.getTerminalId(), from, to, less, pageable );
            } else {
                entities = upTimeRepository.findByUpTimeIdTerminalIdAndUpTimeIdDateBetweenOrderByUpTimeIdDateDesc( sc.getTerminalId(), from, to, pageable );
            }
            return entities;
        } catch ( ParseException e ) {
            throw new UptimeException( CatalogError.UPTIME_ERROR_DATES );
        }
    }

    private Pageable getPageableRequest( int page, int results ) {
        if ( results == 0 ) {
            return PageRequest.of( page, 30 );
        } else {
            return PageRequest.of( page, results );
        }
    }

    private int getPercentageOfAvailability( int percentage ) {
        return ( percentage * 86400 ) / 100;
    }

    private void log( ReportSearchConditions reportSearchConditions ) {
        if ( reportSearchConditions.getTerminalId() != null && !reportSearchConditions.getTerminalId().isEmpty() ) {
            if ( LOGGER.isInfoEnabled() ) {
                LOGGER.info( String.format( "Buscando registros por el cajero %s", reportSearchConditions.getTerminalId() ) );
            }
        } else if ( reportSearchConditions.getFiid() != null && !reportSearchConditions.getFiid().isEmpty() ) {
            if ( LOGGER.isInfoEnabled() ) {
                LOGGER.info( String.format( "Buscando registros por institución %s", reportSearchConditions.getFiid() ) );
            }
        } else {
            if ( LOGGER.isInfoEnabled() ) {
                LOGGER.info( "Buscando por toda la red de cajeros Prosa" );
            }
        }
        log2( reportSearchConditions );
    }

    private void log2( ReportSearchConditions reportSearchConditions ) {
        if ( LOGGER.isInfoEnabled() ) {
            LOGGER.info( "Criterios de búsqueda" );
        }
        if ( reportSearchConditions.getForm() != null && reportSearchConditions.getTo() != null && LOGGER.isInfoEnabled() ) {
            LOGGER.info( "Fechas" );
            LOGGER.info( String.format( "Desde %s Hasta %s", reportSearchConditions.getForm(), reportSearchConditions.getTo() ) );
        }
        if ( reportSearchConditions.getGreaterThan() > 0 && LOGGER.isInfoEnabled() ) {
            LOGGER.info( "Uptime" );
            LOGGER.info( String.format( "Uptimes mayores a %d", reportSearchConditions.getGreaterThan() ) );
        } else if ( reportSearchConditions.getLessThan() > 0 && LOGGER.isInfoEnabled() ) {
            LOGGER.info( "Uptime" );
            LOGGER.info( String.format( "Uptimes menores a %d", reportSearchConditions.getLessThan() ) );
        }
        if ( LOGGER.isInfoEnabled() ) {
            LOGGER.info( "Paginación" );
            if ( reportSearchConditions.getResults() == 0 ) {
                LOGGER.info( String.format( "Pagina %d, Resultados por defecto %d", reportSearchConditions.getPage(), 10 ) );
            } else {
                LOGGER.info( String.format( "Pagina %d, Resultados %d", reportSearchConditions.getPage(), reportSearchConditions.getResults() ) );
            }
        }
    }

    private IDFEntityKey lookupUser( ReportSearchConditions conditions ) throws UptimeException {
        try {
            IDFEntityKey key = userSession.getIDFForCurrentUser();
            if ( !key.getFiid().equals( "PROS" ) ) {
                conditions.setFiid( key.getFiid() );
            }
            return key;
        } catch ( UserNotFoundException e ) {
            throw new UptimeException( e.getError() );
        }
    }

    private Optional < ATDUpTimeEntity > secureSearchByATM( ReportSearchConditions searchConditions, IDFEntityKey key ) {
        if ( !key.getFiid().equals( "PROS" ) ) {
            return atdUpTimeRepository.findByTerminalIdAndIdf( searchConditions.getTerminalId(), key );
        } else {
            return atdUpTimeRepository.findById( searchConditions.getTerminalId() );
        }

    }

}
