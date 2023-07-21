package mx.com.prosa.nabhi.dash.redcat.forcedcutover;

import mx.com.prosa.nabhi.misc.domain.redcat.entity.IDFForRedcatEntity;
import mx.com.prosa.nabhi.misc.domain.redcat.repository.IDFForRedcatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@EnableScheduling
@Configuration
public class ForcedCutOver implements SchedulingConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger( ForcedCutOver.class );
    private final IDFForRedcatRepository idfRepository;
    private ForcedCutOverListener forcedCutOverListener;
    private ScheduledTaskRegistrar taskRegistrarMain;
    private final Map < String, TriggerTask > taskMap = new LinkedHashMap <>();

    @Autowired
    public ForcedCutOver( IDFForRedcatRepository idfRepository, RedcatJob redcatJob ) {
        this.idfRepository = idfRepository;
        this.forcedCutOverListener = redcatJob;
    }

    @Override
    public void configureTasks( ScheduledTaskRegistrar taskRegistrar ) {
        this.taskRegistrarMain = taskRegistrar;
        taskRegistrar.setScheduler( poolScheduler() );
        List <IDFForRedcatEntity> entities = idfRepository.findAll();
        for ( IDFForRedcatEntity entity : entities ) {
            if ( LOGGER.isInfoEnabled() ) {
                LOGGER.info( String.format( "Configurado Job para corte forzado para la institución %s", entity.getFiid() ) );
            }
            TriggerTask task = buildTask( entity );
            taskMap.put( entity.getFiid(), task );
            taskRegistrar.addTriggerTask( task );
        }
    }

    public void removeTriggerTask( String fiid ) {
        TriggerTask task = taskMap.get( fiid );
        if ( task == null ) {
            if ( LOGGER.isInfoEnabled() ) {
                LOGGER.info( String.format( "No existe un corte forzado para la institución %s", fiid ) );
            }
        } else {
            taskMap.remove( fiid );
            taskRegistrarMain.getTriggerTaskList().remove( task );
        }
    }

    private TriggerTask buildTask( IDFForRedcatEntity entity ) {
        return new TriggerTask( () -> {
            if ( forcedCutOverListener == null ) {
                forcedCutOverListener = fiid -> {
                    if ( LOGGER.isInfoEnabled() ) {
                        LOGGER.info( String.format( "Corte forzado ejecutado para la Institución %s, no hay listeners configurados", entity.getFiid() ) );
                    }
                };
            }
            updateDays( entity );
            forcedCutOverListener.notifyForcedCutOver( entity.getFiid() );
        }, triggerContext -> getBusiness( entity.getFiid() ).getTime() );
    }

    public void updateDays( IDFForRedcatEntity entity ) {
        Optional < IDFForRedcatEntity > optional = idfRepository.findById( entity.getFiid() );
        if ( optional.isPresent() ) {
            entity = optional.get();
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
            Calendar calendar = defaultDate();
            long timestamp = calendar.getTime().getTime() + 86400 * 1000;
            entity.setCurrentBusinessDay( sdf.format( new Date( timestamp ) ) );
            entity.setNextBusinessDay( sdf.format( new Date( timestamp + (86400*1000) ) ) );
            if ( entity.getForcedCutOver() == null ){
                entity.setForcedCutOver( "20:00");
            }
            idfRepository.save( entity );
        } else {
            removeTriggerTask( entity.getFiid() );
        }
    }

    private Calendar getBusiness( String fiid ) {
        Optional < IDFForRedcatEntity > optional = idfRepository.findById( fiid );
        Calendar nextExecutionTime = new GregorianCalendar();
        if ( optional.isPresent() ) {
            IDFForRedcatEntity entity = optional.get();
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            String businessDay = entity.getCurrentBusinessDay() + " " + entity.getForcedCutOver();
            try {
                Date workingDate = sdf.parse( businessDay );
                Date systemDate = new Date( System.currentTimeMillis() );
                if ( workingDate.getTime() < systemDate.getTime() ) {
                    nextExecutionTime.setTime( defaultDate().getTime() );
                    if ( LOGGER.isWarnEnabled() ) {
                        LOGGER.warn( String.format( "Horario de corte invalido, el horario actual %s es mayor al configurado %s para la Institución %s", sdf.format( systemDate ), sdf.format( workingDate ), entity.getFiid() ) );
                        LOGGER.warn( "Se coloca por default 22:00:00 todos los dias, hasta que se corrija el problema" );
                    }
                } else {

                    if ( LOGGER.isInfoEnabled() ) {
                        LOGGER.info( String.format( "Horario de corte para la Institución %s %s", entity.getFiid(), businessDay ) );
                    }
                    nextExecutionTime.setTime( workingDate );
                }
            } catch ( ParseException e ) {
                LOGGER.error( String.format( "Horario de corte invalido, formato invalido, solo se permite dd/MM/yyyy HH:mm y se tiene configurado %s para la Institución %s", businessDay, entity.getFiid() ) );
                LOGGER.error( "Se coloca por default 22:00:00 todos los dias, hasta que se corrija el problema" );
                nextExecutionTime.setTime( defaultDate().getTime() );
            }
        } else {
            nextExecutionTime.setTime( defaultDate().getTime() );
        }
        return nextExecutionTime;
    }

    private Calendar defaultDate() {
        Calendar defaultDate = new GregorianCalendar();
        defaultDate.set( Calendar.HOUR_OF_DAY, 22 );
        defaultDate.set( Calendar.MINUTE, 0 );
        defaultDate.set( Calendar.SECOND, 0 );
        return defaultDate;
    }


    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix( "ForcedCutOver" );
        scheduler.setPoolSize( 1 );
        scheduler.initialize();
        return scheduler;
    }
}
