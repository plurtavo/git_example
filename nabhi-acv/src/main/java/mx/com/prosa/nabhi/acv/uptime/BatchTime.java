package mx.com.prosa.nabhi.acv.uptime;

import mx.com.prosa.nabhi.misc.domain.acv.entity.ATDForLogEntity;
import mx.com.prosa.nabhi.misc.domain.acv.entity.ATMLogConectionEntity;
import mx.com.prosa.nabhi.misc.domain.acv.entity.UpTimeEntity;
import mx.com.prosa.nabhi.misc.domain.acv.entity.composite.UpTimeIdentity;
import mx.com.prosa.nabhi.misc.domain.acv.repository.ATDForLogRepository;
import mx.com.prosa.nabhi.misc.domain.acv.repository.ATMLogConnectionRepository;
import mx.com.prosa.nabhi.misc.domain.acv.repository.UpTimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class BatchTime {

    private static final Logger LOGGER = LoggerFactory.getLogger( BatchTime.class );
    private final SimpleDateFormat formatKey = new SimpleDateFormat( "yyyy-MM-dd" );
    private final SimpleDateFormat formatValues = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    private final DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    @Value( "${nabhi.uptime.remove.log}" )
    private boolean remove;

    private final ATMLogConnectionRepository atmLogConnectionRepository;
    private final ATDForLogRepository atdRepository;
    private final UpTimeRepository upTimeRepository;

    @Autowired
    public BatchTime( ATMLogConnectionRepository atmLogConnectionRepository, ATDForLogRepository atdRepository, UpTimeRepository upTimeRepository ) {
        this.atmLogConnectionRepository = atmLogConnectionRepository;
        this.atdRepository = atdRepository;
        this.upTimeRepository = upTimeRepository;
    }

    private void doGenerate() {
        List < ATDForLogEntity > entities = atdRepository.findAll();
        if ( LOGGER.isInfoEnabled() ){
            LOGGER.info( String.format( "Total de cajeros encontrados %s", entities.size() ) );
        }
        for ( ATDForLogEntity entity : entities ) {
            buildUpTime( entity );
        }
    }

    private void buildUpTime( ATDForLogEntity entity ) {
        List < ATMLogConectionEntity > logs = atmLogConnectionRepository.findAllByAtdOrderByIdAsc( entity );
        if ( !logs.isEmpty() ){
            if ( LOGGER.isInfoEnabled() ) {
                LOGGER.info( String.format( "Construyendo persistencia para el cajero %s", entity.getTerminalId() ) );
            }
        }else {
            if ( LOGGER.isInfoEnabled() ) {
                LOGGER.info( String.format( "El cajero %s no tiene logs de conexiones", entity.getTerminalId() ) );
            }
        }
        List < ATMLogConectionEntity > logsRemove = new ArrayList <>();
        List < UpTime > upTimes = new ArrayList <>();
        for ( ATMLogConectionEntity log : logs ) {
            if ( log.getUp() != null && log.getDown() != null && log.getAtd() != null ) {
                String dv1 = dateFormat.format( new Date( log.getUp().getTime() ) );
                String dv2 = dateFormat.format( new Date( log.getDown().getTime() ) );
                UpTime up = new UpTime( log.getSessionId(), dv1, dv2, log.getAtd().getTerminalId() );
                upTimes.add( up );
                logsRemove.add( log );
            }else {
                if ( LOGGER.isInfoEnabled() ) {
                    LOGGER.info( String.format( "El log con ID %s y WS Session %s tiene datos inválidos, no se contempla para el reporte", log.getId(), log.getSessionId()  ) );
                    LOGGER.info( "Verificar manualmente los datos, en la siguiente ejecución se contemplaran");
                }
            }
        }
        buildUpTimePersistence( upTimes, entity.getTerminalId() );
        if ( remove ) {
            atmLogConnectionRepository.deleteAll( logsRemove );
        }
    }

    private void buildUpTimePersistence( List < UpTime > upTimes, String terminalId ) {
        Map < Long, Long > longs = new LinkedHashMap <>();
        for ( UpTime upTime : upTimes ) {
            calculateUpTime( upTime, longs );
        }
        for ( Map.Entry < Long, Long > entry : longs.entrySet() ) {
            Date date = new Date( entry.getKey() * 1000 );
            UpTimeEntity entity = new UpTimeEntity();
            UpTimeIdentity identity = new UpTimeIdentity();
            identity.setTerminalId( terminalId );
            identity.setDate( new java.sql.Date( date.getTime() ) );
            entity.setUpTimeIdentity( identity );
            entity.setUpTime( Math.toIntExact( entry.getValue() ) );
            upTimeRepository.save( entity );
        }
    }

    private void calculateUpTime( UpTime upTime, Map < Long, Long > longs ) {
        try {
            long dk = formatKey.parse( upTime.getUp() ).getTime() / 1000;
            long dv1 = formatValues.parse( upTime.getUp() ).getTime() / 1000;
            long dv2 = formatValues.parse( upTime.getDown() ).getTime() / 1000;
            long cv = 0;
            if ( longs.get( dk ) != null ) {
                cv = longs.get( dk );
            }
            if ( dv2 > ( dk + 86400 ) ) {
                UpTime upTime1 = calculateUpTimeForLongDays( upTime, dk, dv1, dv2, cv, longs );
                calculateUpTime( upTime1, longs );
            } else {
                long v = dv2 - dv1 + 1;
                cv += v;
                longs.put( dk, cv );
            }
        } catch ( ParseException e ) {
            LOGGER.error( String.format( "Error al convertir el timestamp del cajero %s", upTime.getUp() ) );
        }
    }


    private UpTime calculateUpTimeForLongDays( UpTime upTime, long dk, long dv1, long dv2, long cv, Map < Long, Long > longs ) throws ParseException {
        String nd = upTime.getUp().substring( 0, 10 ) + " 23:59:59";
        long dv3 = formatValues.parse( nd ).getTime() / 1000;
        long v = dv3 - dv1 + 1;
        cv += v;
        longs.put( dk, cv );
        long ndl = dk + 86400;
        Date up = new Date( ndl * 1000 );
        Date down = new Date( ( dv2 ) * 1000 );
        return new UpTime( upTime.getSessionId(), dateFormat.format( up ), dateFormat.format( down ), upTime.getId() );
    }

    @Scheduled( cron = "${nabhi.uptime.batch.cron}" )
    private void batchReport() {
        if ( LOGGER.isInfoEnabled() ) {
            LOGGER.info( "Ejecutado proceso automático para generación de Up Times de la red de cajeros" );
            LOGGER.info( "La información generada quedara almacenada en la tabla TBL_UP_TIME" );
            if ( remove ) {
                LOGGER.info( "Los Logs de conexión del los cajeros serán eliminados después del proceso" );
            }
        }
        doGenerate();
    }
}
