package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IRCPTService;
import mx.com.prosa.nabhi.dash.core.IDataBaseService;
import mx.com.prosa.nabhi.dash.core.IInstitutionService;
import mx.com.prosa.nabhi.dash.core.receipt.ReceiptTest;
import mx.com.prosa.nabhi.misc.domain.complete.entity.RCPTEntity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.RCPTRepository;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.ReceiptException;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.RCPT;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import us.gonet.iso8583.constants.atm.TranCodes;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RCPTService implements IRCPTService {

    private final RCPTRepository rcptRepository;
    private final ModelMapper mapper;
    private final IDataBaseService dataBaseService;
    private final IInstitutionService institutionService;
    private final ReceiptTest receiptTest;
    private static final Logger LOGGER = LoggerFactory.getLogger( RCPTService.class );
    private static final String FIID_PROSA = "PROS";
    private static final String TABLE = "RECIBOS";
    private static final String ERROR = "para el modulo " + TABLE;
    private static final Class < RCPTEntity > domainClass = RCPTEntity.class;
    private static final Class < RCPT > dtoClass = RCPT.class;
    private final Type listType = new TypeToken < List < RCPT > >() {
    }.getType();

    @Autowired
    public RCPTService( RCPTRepository rcptRepository, ModelMapper mapper, IDataBaseService dataBaseService, IInstitutionService institutionService, ReceiptTest receiptTest ) {
        this.rcptRepository = rcptRepository;
        this.mapper = mapper;
        this.dataBaseService = dataBaseService;
        this.institutionService = institutionService;
        this.receiptTest = receiptTest;
    }

    @Override
    public String create( RCPT value ) throws DataBaseException {
        try {
            RCPTEntity entity = mapper.map( value, domainClass );
            entity.setName( "RCPT" + "-" + entity.getFiid() + "-" + entity.getTranCode() + "-" +
                    ( entity.isCostumer() ? "CLIENTE" : "JOURNAL" ) );
            rcptRepository.save( entity );
            return "RCPT Creado correctamente";
        } catch ( EntityNotFoundException e ) {
            throw new DataBaseException( CatalogError.RCPT_NOT_REFERENCE );
        } catch ( DataIntegrityViolationException e ) {
            throw new DataBaseException( CatalogError.RCPT_DUPLICATE_NAME );
        } catch ( InvalidDataAccessApiUsageException e ) {
            throw new DataBaseException( CatalogError.RCPT_NOT_REFERENCE_2 );
        }
    }

    @Override
    public String modify( RCPT value ) throws DataBaseException {
        RCPTEntity entity = mapper.map( value, domainClass );
        try {
            if ( rcptRepository.findById( value.getId() ).isPresent() ) {
                entity = rcptRepository.save( entity );
                String str = String.format( "Script de recibo con id %d y nombre %s actualizado correctamente", entity.getId(), entity.getName() );
                if ( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( str );
                }
                return str;
            } else {
                throw new DataBaseException( CatalogError.RCPT_UPDATE_NOT_FOUND );
            }
        } catch ( InvalidDataAccessApiUsageException | DataIntegrityViolationException e ) {
            throw new DataBaseException( CatalogError.RCPT_NOT_REFERENCE_2 );
        } catch ( DataAccessException e ) {
            throw new DataBaseException( CatalogError.RCPT_NOT_REFERENCE );
        }
    }

    @Override
    public String delete( RCPT value ) throws DataBaseException {
        RCPTEntity entity = mapper.map( value, domainClass );
        try {
            rcptRepository.delete( entity );
            String str = String.format( "Script de recibo con id %d y nombre %s eliminado correctamente", entity.getId(), entity.getName() );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( str );
            }
            return str;
        } catch ( DataIntegrityViolationException e ) {
            throw new DataBaseException( CatalogError.RCPT_NOT_REFERENCE_2 );
        } catch ( DataAccessException e ) {
            throw new DataBaseException( CatalogError.RCPT_NOT_REFERENCE );
        }
    }

    @Override
    public RCPT findById( String name ) throws DataBaseException {
        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( String.format( "Buscando RCPT con llave %s", name ) );
        }
        Optional < RCPTEntity > optional = rcptRepository.findByName( name );
        if ( optional.isPresent() ) {
            return mapper.map( optional.get(), dtoClass );
        } else {
            throw new DataBaseException( CatalogError.RCPT_FIND_GROUP_EMPTY_RESULT_SET );
        }
    }

    @Override
    public List < RCPT > findAll() throws DataBaseException {
        try {
            List < RCPTEntity > entities;
            IDFEntityKey key = institutionService.lookupUser();
            if ( key.getFiid().equals( FIID_PROSA ) ) {
                entities = dataBaseService.findByAll( rcptRepository );
            } else {
                entities = rcptRepository.findAllByFiid( key.getFiid() );
            }
            if ( !entities.isEmpty() ){
                return mapper.map( entities, listType );
            }else {
                throw new DataBaseException( CatalogError.RCPT_FIND_GROUP_EMPTY_RESULT_SET );
            }
        } catch ( DataBaseException | IDFException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }

    }

    @Override
    public List < String > findAllOnlyNames() throws DataBaseException {
        List < RCPT > rcpts = findAll();
        List < String > names = new ArrayList <>();
        for ( RCPT rcpt : rcpts ) {
            names.add( rcpt.getName() );
        }
        return names;
    }

    @Override
    public String test( String name ) throws DataBaseException, ReceiptException {
        Optional < RCPTEntity > optional = rcptRepository.findByName( name );
        if ( optional.isPresent() ) {
            TranCodes tranCodes = TranCodes.getValue( optional.get().getTranCode() );
            return receiptTest.createReceipt( tranCodes, optional, optional.get().isCostumer() ).getTicket();
        } else {
            throw new DataBaseException( CatalogError.RCPT_FIND_GROUP_EMPTY_RESULT_SET );
        }
    }
}
