package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IRedcatService;
import mx.com.prosa.nabhi.dash.model.redcat.RedcatSearchConditions;
import mx.com.prosa.nabhi.dash.redcat.forcedcutover.ForcedCutOver;
import mx.com.prosa.nabhi.dash.redcat.forcedcutover.RedcatJob;
import mx.com.prosa.nabhi.dash.security.UserSession;
import mx.com.prosa.nabhi.misc.domain.redcat.dto.Redcat;
import mx.com.prosa.nabhi.misc.domain.redcat.entity.IDFForRedcatEntity;
import mx.com.prosa.nabhi.misc.domain.redcat.entity.RedcatEntity;
import mx.com.prosa.nabhi.misc.domain.redcat.repository.IDFForRedcatRepository;
import mx.com.prosa.nabhi.misc.domain.redcat.repository.RedcatRepository;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.dash.RedcatException;
import mx.com.prosa.nabhi.misc.exception.dash.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class RedcatService implements IRedcatService {

    private final ModelMapper modelMapper;
    private final RedcatRepository redcatRepository;
    private final RedcatJob redcatJob;
    private final IDFForRedcatRepository idfForRedcatRepository;
    private final UserSession userSession;
    private final ForcedCutOver forcedCutOver;

    private final SimpleDateFormat formatter = new SimpleDateFormat( "dd/MM/yyyy" );
    private static final Logger LOG = LoggerFactory.getLogger( RedcatService.class );


    @Autowired
    public RedcatService( RedcatRepository redcatRepository, RedcatJob redcatJob, ForcedCutOver forcedCutOver, IDFForRedcatRepository idfForRedcatRepository,
                          ModelMapper modelMapper, UserSession userSession ) {
        this.redcatRepository = redcatRepository;
        this.modelMapper = modelMapper;
        this.redcatJob = redcatJob;
        this.idfForRedcatRepository = idfForRedcatRepository;
        this.userSession = userSession;
        this.forcedCutOver = forcedCutOver;
    }

    @Override
    public String forceCutForATD( RedcatSearchConditions conditions ) throws RedcatException {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Generando corte forzado de manera manual para cajero: %s", conditions.getTerminalId() ) );
        }
        return redcatJob.forceCutByATM( conditions.getTerminalId(), conditions.getFiid() );
    }

    @Override
    public String forceCutForFIID( RedcatSearchConditions conditions ) throws RedcatException {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Generando corte forzado de manera manual para FIID: %s", conditions.getFiid() ) );
        }
        findIdfUser( conditions );
        IDFForRedcatEntity entity = new IDFForRedcatEntity();
        entity.setFiid( conditions.getFiid() );
        if ( !isDone( conditions.getFiid() ) ) {
            forcedCutOver.updateDays( entity );
            return redcatJob.forceCutByFiid( conditions.getFiid(), false );
        }
        if ( LOG.isErrorEnabled() ) {
            LOG.error( CatalogError.REDCAT_ERROR_FORCECUT_ARLEADY_DONE.toString() );
        }
        throw new RedcatException( CatalogError.REDCAT_ERROR_FORCECUT_ARLEADY_DONE );
    }

    @Override
    public List < Redcat > findRedcat( RedcatSearchConditions conditions ) throws RedcatException {
        List < RedcatEntity > entityList;
        try {
            java.util.Date form = formatter.parse( conditions.getForm() );
            java.util.Date to = formatter.parse( conditions.getTo() );
            if ( conditions.getTerminalId() != null ) {
                if ( LOG.isDebugEnabled() ) {
                    LOG.info( "Buscando reporte redcat por cajero" );
                }
                entityList = redcatRepository.findByTermIdAndStartDateIsBetween( conditions.getTerminalId(), form, to );
            } else if ( conditions.getFiid() != null ) {
                if ( LOG.isDebugEnabled() ) {
                    LOG.info( "Buscando reporte redcat por institución" );
                }
                findIdfUser( conditions );
                entityList = redcatRepository.findByFiidAndStartDateIsBetween( conditions.getFiid(), form, to );
            } else {
                throw new RedcatException( CatalogError.REDCAT_ERROR_PARAMS );
            }

            if ( !entityList.isEmpty() ) {
                List < Redcat > list = new ArrayList <>();
                for ( RedcatEntity entity : entityList ) {
                    list.add( modelMapper.map( entity, Redcat.class ) );
                }
                return list;
            }
            LOG.error( "Lista de reportes vacia para esas fechas" );
            throw new RedcatException( CatalogError.REDCAT_EMPTY_REPORT );
        } catch ( ParseException e ) {
            LOG.error( "Error con el formato de la fecha de entrada" );
            throw new RedcatException( CatalogError.REDCAT_ERROR_DATES );
        }

    }

    @Override
    public ByteArrayOutputStream findExcelRedcat( RedcatSearchConditions conditions ) throws RedcatException {
        List < Redcat > redcatList = findRedcat( conditions );
        if ( !redcatList.isEmpty() ) {
            if ( conditions.getFiid() == null ) {
                conditions.setFiid( redcatList.get( 0 ).getFiid() );
            }
            return redcatJob.createBinaryExcel( redcatList, conditions.getFiid() );
        }
        LOG.error( "Error porque la lista de reporte esta vacía" );
        throw new RedcatException( CatalogError.REDCAT_EMPTY_REPORT );
    }

    private void findIdfUser( RedcatSearchConditions conditions ) throws RedcatException {
        try {
            IDFEntityKey key = userSession.getIDFForCurrentUser();
            if ( !key.getFiid().equals( "PROS" ) ) {
                LOG.info( "Usuario administrador de prosa" );
                conditions.setFiid( key.getFiid() );
            }
        } catch ( UserNotFoundException e ) {
            throw new RedcatException( CatalogError.USER_NOT_FOUND );
        }
    }

    public boolean isDone( String fiid ) throws RedcatException {
        Optional < IDFForRedcatEntity > optional = idfForRedcatRepository.findById( fiid );
        if ( optional.isPresent() ) {
            IDFForRedcatEntity entity = optional.get();
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            String businessDay = entity.getCurrentBusinessDay() + " " + entity.getForcedCutOver();
            try {
                Date workingDate = sdf.parse( businessDay );
                Date systemDate = new Date( System.currentTimeMillis() );
                return workingDate.getTime() > systemDate.getTime();
            } catch ( ParseException e ) {
                LOG.error( String.format( "%s%s", CatalogError.REDCAT_ERROR_INVALID_FORCECUT_DATE, fiid ) );
                throw new RedcatException( CatalogError.REDCAT_ERROR_INVALID_FORCECUT_DATE );
            }
        }
        if ( LOG.isErrorEnabled() ) {
            LOG.error( CatalogError.REDCAT_ERROR_IDF_NOT_FOUND.toString() );
        }
        throw new RedcatException( CatalogError.REDCAT_ERROR_IDF_NOT_FOUND );
    }

}

