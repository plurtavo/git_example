package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.IImageService;
import mx.com.prosa.nabhi.dash.core.IDataBaseService;
import mx.com.prosa.nabhi.dash.core.IInstitutionService;
import mx.com.prosa.nabhi.misc.domain.complete.entity.ImageEntity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.ImageRepository;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.Image;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final ModelMapper mapper;
    private final IDataBaseService dataBaseService;
    private final IInstitutionService institutionService;
    private static final Logger LOGGER = LoggerFactory.getLogger( ImageService.class );
    private static final String FIID_PROSA = "PROS";
    private static final String TABLE = "Imágenes";
    private static final String ERROR = "para el modulo " + TABLE;
    private static final Class < ImageEntity > domainClass = ImageEntity.class;
    private static final Class < Image > dtoClass = Image.class;
    private final Type listType = new TypeToken < List < Image > >() {
    }.getType();

    @Autowired
    public ImageService( ImageRepository imageRepository, ModelMapper mapper, IDataBaseService dataBaseService, IInstitutionService institutionService ) {
        this.imageRepository = imageRepository;
        this.mapper = mapper;
        this.dataBaseService = dataBaseService;
        this.institutionService = institutionService;
    }

    @Override
    public String create( Image value ) throws DataBaseException {
        try {
            ImageEntity entity = mapper.map( value, domainClass );
            dataBaseService.create( imageRepository, entity.getName(), entity );
            return "Imagen Creado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public String modify( Image value ) throws DataBaseException {
        ImageEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.modify( imageRepository, entity.getName(), entity );
            return "Imagen Modificado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public String delete( Image value ) throws DataBaseException, IDFException {
        ImageEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.delete( imageRepository, entity.getName() );
            return "Imagen Eliminado correctamente";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public Image findById( String name, String category ) throws DataBaseException {
        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( String.format( "Buscando Imagen con llave %s %s", name, category ) );
        }
        Optional < ImageEntity > optional = imageRepository.findByNameAndCategory( name, category );
        if ( optional.isPresent() ) {
            return mapper.map( optional.get(), dtoClass );
        } else {
            throw new DataBaseException( CatalogError.DATABASE_FIND_NO_EXIST, ERROR );
        }
    }

    @Override
    public List < Image > findAll() throws DataBaseException, IDFException {
        try {
            List < ImageEntity > entities;
            IDFEntityKey key = institutionService.lookupUser();
            if ( key.getFiid().equals( FIID_PROSA ) ) {
                entities = dataBaseService.findByAll( imageRepository );
            } else {
                entities = imageRepository.findAllByFiid( key.getFiid() );
            }
            if ( !entities.isEmpty() ){
                return mapper.map( entities, listType );
            }else {
                throw new DataBaseException( CatalogError.IMAGE_FIND_GROUP_EMPTY_RESULT_SET );
            }
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }

    }

    @Override
    public List < String > findAllOnlyNames() throws DataBaseException, IDFException {
        List < Image > images = findAll();
        List < String > names = new ArrayList <>();
        for ( Image image : images ) {
            names.add( image.getCategory() + ":" + image.getName() );
        }
        return names;
    }
}
