package mx.com.prosa.nabhi.acv.storage;

import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.acv.FileException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class FileStorage implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger( FileStorage.class );
    @Value( "${nabhi.files.storage.directory}" )
    private String storageLocation;
    private final Map < String, Path > storageLocations = new LinkedHashMap <>();

    @Override
    @PostConstruct
    public void init() {
        Path root = Paths.get( storageLocation ).toAbsolutePath().normalize();
        if ( !root.toFile().exists() ) {
            try {
                Files.createDirectories( root );
                storageLocations.put( "root", root );
            } catch ( IOException e ) {
                LOGGER.error( String.format( "No se puede construir el servicio de almacenamiento %s", e.getMessage() ) );
            }
        } else {
            storageLocations.put( "root", root );
            File file = root.toFile();
            String[] directories = file.list( ( current, name ) -> new File( current, name ).isDirectory() );
            if ( directories != null ) {
                for ( String directory : directories ) {
                    Path nroot = Paths.get( storageLocation + "/" + directory ).toAbsolutePath().normalize();
                    storageLocations.put( "/" + directory, nroot );
                    if ( LOGGER.isDebugEnabled() ) {
                        LOGGER.debug( String.format( "Cargando el subdirectorio %s", storageLocation + "/" + directory ) );
                    }
                    loadSubdirectories( nroot, directory );
                }
            }

        }
    }

    private void loadSubdirectories( Path root, String directory ) {
        File file = root.toFile();
        String[] directories = file.list( ( current, name ) -> new File( current, name ).isDirectory() );
        if ( directories != null ) {
            for ( String sub : directories ) {
                Path nroot = Paths.get( storageLocation + "/" + directory + "/" + sub ).toAbsolutePath().normalize();
                storageLocations.put( "/" + directory + "/" + sub, nroot );
                if ( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( String.format( "Cargando el subdirectorio %s", storageLocation + "/" + directory + "/" + sub ) );
                }
            }
        }

    }

    @Override
    public void init( String path ) throws FileException {
        Path nPath = Paths.get( storageLocation + path ).toAbsolutePath().normalize();
        if ( !nPath.toFile().exists() ) {
            try {
                Files.createDirectories( nPath );
            } catch ( IOException e ) {
                throw new FileException( CatalogError.FILE_NO_STORAGE );
            }
        }
        storageLocations.put( path, nPath );
    }

    @Override
    public String store( MultipartFile file, String path ) throws FileException {
        String fileName = StringUtils.cleanPath( Objects.requireNonNull( file.getOriginalFilename() ) );
        Path fileStorageLocation;
        try {
            if ( fileName.contains( ".." ) ) {
                throw new FileException( CatalogError.FILE_NO_PATH );
            }
            fileStorageLocation = storageLocations.get( "/" + path );
            Path targetLocation = fileStorageLocation.resolve( fileName );
            Files.copy( file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING );
            return path + "/" + fileName;
        } catch ( IOException ex ) {
            throw new FileException( CatalogError.FILE_NOT_STORAGE_FILE );
        }
    }

    @Override
    public Resource loadAsResource( String filename, String path ) throws FileException {
        try {
            Path fileStorageLocation = storageLocations.get( "/" + path );
            Path filePath = fileStorageLocation.resolve( filename ).normalize();

            Resource resource = new UrlResource( filePath.toUri() );
            if ( resource.exists() ) {
                return resource;
            } else {
                throw new FileException( CatalogError.FILE_NOT_FOUND );
            }
        } catch ( MalformedURLException ex ) {
            throw new FileException( CatalogError.FILE_INVALID_PATH );
        }
    }

    @Override
    public void delete( String path ) throws FileException {
        try {
            Path fileStorageLocation = storageLocations.get( "/" + path );
            FileUtils.deleteDirectory( fileStorageLocation.toFile() );
        } catch ( IOException e ) {
            throw new FileException( CatalogError.FILE_NOT_DELETE );
        }
    }
}
