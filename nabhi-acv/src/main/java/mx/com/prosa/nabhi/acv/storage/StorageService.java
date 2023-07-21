package mx.com.prosa.nabhi.acv.storage;

import mx.com.prosa.nabhi.misc.exception.acv.FileException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    void init( String path ) throws FileException;

    String store( MultipartFile file, String path ) throws FileException;

    Resource loadAsResource( String filename, String path ) throws FileException;

    void delete( String path ) throws FileException;

}