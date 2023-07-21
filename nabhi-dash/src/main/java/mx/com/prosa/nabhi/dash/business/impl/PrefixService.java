package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IPrefixService;
import mx.com.prosa.nabhi.dash.core.IDataBaseService;
import mx.com.prosa.nabhi.dash.core.IInstitutionService;
import mx.com.prosa.nabhi.misc.domain.complete.entity.BINEntity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.BINRepository;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.Prefix;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrefixService implements IPrefixService {

    private final BINRepository binRepository;
    private final ModelMapper mapper;
    private final IDataBaseService dataBaseService;
    private final IInstitutionService institutionService;
    private static final Logger LOGGER = LoggerFactory.getLogger( PrefixService.class );
    private static final String FIID_PROSA = "PROS";
    private static final String TABLE = "BINES";
    private static final String ERROR = "para el modulo " + TABLE;
    private static final Class < BINEntity > domainClass = BINEntity.class;
    private static final Class < Prefix > dtoClass = Prefix.class;
    private final Type listType = new TypeToken < List < Prefix > >() {
    }.getType();

    @Autowired
    public PrefixService( BINRepository binRepository, ModelMapper mapper, IDataBaseService dataBaseService, IInstitutionService institutionService ) {
        this.binRepository = binRepository;
        this.mapper = mapper;
        this.dataBaseService = dataBaseService;
        this.institutionService = institutionService;
    }

    @Override
    public String create( Prefix value ) throws DataBaseException {
        try {
            BINEntity entity = mapper.map( value, domainClass );
            binRepository.save( entity );
            return "Prefijo Creado correctamente";
        } catch ( EntityNotFoundException e ) {
            throw new DataBaseException( CatalogError.BIN_NOT_REFERENCE );
        } catch ( DataIntegrityViolationException e ) {
            throw new DataBaseException( CatalogError.BIN_DUPLICATE_NAME );
        } catch ( InvalidDataAccessApiUsageException e ) {
            throw new DataBaseException( CatalogError.BIN_NOT_REFERENCE_2 );
        }
    }

    @Override
    public String modify( Prefix value ) throws DataBaseException {
        BINEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.modify( binRepository, entity.getId(), entity );
            return "BIN Modificado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public String delete( Prefix value ) throws DataBaseException, IDFException {
        BINEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.delete( binRepository, entity.getId() );
            return "BIN Eliminado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public Prefix findById( String bin, int panLen, int binLen ) throws DataBaseException {

        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( String.format( "Buscando BIN con llave %s %s %s", bin, panLen, binLen ) );
        }
        Optional < BINEntity > optional = binRepository.findByBinAndBinLenAndPanLen( bin, binLen, panLen );
        if ( optional.isPresent() ) {
            return mapper.map( optional.get(), dtoClass );
        } else {
            throw new DataBaseException( CatalogError.BIN_FIND_GROUP_EMPTY_RESULT_SET );
        }
    }

    @Override
    public List < Prefix > findAll() throws DataBaseException, IDFException {
        try {
            List < BINEntity > entities;
            IDFEntityKey key = institutionService.lookupUser();
            if ( key.getFiid().equals( FIID_PROSA ) ) {
                entities = binRepository.findAll();
            } else {
                entities = binRepository.findAllByFiidOrderByBin( key.getFiid() );
            }
            if ( !entities.isEmpty() ) {
                return mapper.map( entities, listType );
            } else {
                throw new DataBaseException( CatalogError.BIN_FIND_GROUP_EMPTY_RESULT_SET );
            }
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }

    }

    @Override
    public List < String > findAllOnlyNames() throws DataBaseException, IDFException {
        List < Prefix > prefixes = findAll();
        List < String > names = new ArrayList <>();
        for ( Prefix prefix : prefixes ) {
            names.add( prefix.getId() + "-" + prefix.getFiid() + "-" + prefix.getBin() + "-" + prefix.getBinLen() + "-" + prefix.getPanLen() );
        }
        return names;
    }
}
