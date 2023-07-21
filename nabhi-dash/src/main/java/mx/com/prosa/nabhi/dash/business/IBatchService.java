package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.exception.acv.FileException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IBatchService {

    String batch( MultipartFile file ) throws FileException;

    Resource loadAsResource( String filename ) throws FileException;


}
