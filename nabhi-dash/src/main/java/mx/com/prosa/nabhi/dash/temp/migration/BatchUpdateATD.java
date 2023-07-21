package mx.com.prosa.nabhi.dash.temp.migration;

import mx.com.prosa.nabhi.dash.temp.old.entity.ATDOldCassetteEntity;
import mx.com.prosa.nabhi.dash.temp.old.repository.ATDOldCassetteRepository;
import mx.com.prosa.nabhi.misc.domain.algorithm.entity.ATDForAlgorithmEntity;
import mx.com.prosa.nabhi.misc.domain.algorithm.repository.ATDForAlgorithmRepository;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Cassette;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Dispenser;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Logical;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Physical;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class BatchUpdateATD {

    @Value( "${OCP}" )
    private boolean isUpdate;
    private static final Logger LOGGER = LoggerFactory.getLogger( BatchUpdateATD.class );
    private final ATDForAlgorithmRepository updateRepository;
    private final ATDOldCassetteRepository oldRepository;
    private final ModelMapper mapper;

    @Autowired
    public BatchUpdateATD( ATDForAlgorithmRepository updateRepository, ATDOldCassetteRepository oldRepository
            , ModelMapper mapper ) {
        this.updateRepository = updateRepository;
        this.oldRepository = oldRepository;
        this.mapper = mapper;
    }


    @PostConstruct
    public void updateATD() {
        if ( isUpdate ) {
            LOGGER.info( "Bandera activa, se comienza con actualizacion de modelos" );
            List < ATDOldCassetteEntity > atdOldEntities = oldRepository.findAll();
            if ( !atdOldEntities.isEmpty() ) {
                LOGGER.info( "Lista de cajeros encontrada" );
                for ( ATDOldCassetteEntity oldEntity : atdOldEntities ) {

                    ATDForAlgorithmEntity entity = mapper.map( oldEntity, ATDForAlgorithmEntity.class );
                    if ( LOGGER.isInfoEnabled() ) {
                        LOGGER.info( String.format( "Comenzando con el reemplazo de modelo conservando datos anteriores del cajero: %s", oldEntity.getTerminalId() ) );
                    }
                    updateDispenser( oldEntity.getTerminalDevices().getDispenser(), replaceCassettes( oldEntity.getTerminalDevices().getDispenser().getCassettes() ),
                            entity );
                    if ( LOGGER.isInfoEnabled() ) {
                        LOGGER.info( String.format( "Modelos reemplazados para el cajero: %s", oldEntity.getTerminalId() ) );
                    }
                    oldEntity.setTerminalDevices( null );
                    oldErrase( entity.getTerminalId() );
                    oldRepository.save( oldEntity );
                    errase( entity.getTerminalId() );
                    updateRepository.save( entity );
                    logUpdateDevice( entity.getTerminalId() );
                }
            }
        }
    }

    private void logUpdateDevice( String terminal ) {
        if ( LOGGER.isInfoEnabled() ) {
            LOGGER.info( String.format( "Dispositivos actualizados para el cajero: %s", terminal ) );
        }
    }

    private void errase( String terminal ) {
        if ( LOGGER.isInfoEnabled() ) {
            LOGGER.info( String.format( "Dispositivos depurados cajero: %s", terminal ) );
            LOGGER.info( String.format( "Actualizando dispositivos de cajero: %s", terminal ) );
        }
    }

    private void oldErrase( String terminal ) {
        if ( LOGGER.isInfoEnabled() ) {
            LOGGER.info( String.format( "Depurando dispositivos del version anterior para el cajero: %s", terminal ) );
        }
    }


    private void updateDispenser( us.gonet.nabhi.misc.model.devices.cdm.Dispenser dispenserOld, List < Cassette > cassettes, ATDForAlgorithmEntity entity ) {
        if ( LOGGER.isInfoEnabled() ) {
            LOGGER.info( String.format( "Reemplazando dispensador del cajero: %s", entity.getTerminalId() ) );
        }
        Dispenser dispenser = mapper.map( dispenserOld, Dispenser.class );
        dispenser.setCassettes( cassettes );
        entity.getTerminalDevices().setDispenser( dispenser );
    }

    private List < Cassette > replaceCassettes( List < us.gonet.nabhi.misc.model.devices.cdm.Cassette > cassettes ) {
        LOGGER.info( "Reemplazando caseteras" );
        List < Cassette > cassetteList = new ArrayList <>();
        Logical logical = new Logical( 0 );
        for ( us.gonet.nabhi.misc.model.devices.cdm.Cassette cassetteOld : cassettes ) {
            Cassette cassette = mapper.map( cassetteOld, Cassette.class );
            cassette.setPhysical( mapper.map( cassetteOld, Physical.class ) );
            cassette.setLogical( logical );
            cassetteList.add( cassette );
        }
        return cassetteList;
    }
}
