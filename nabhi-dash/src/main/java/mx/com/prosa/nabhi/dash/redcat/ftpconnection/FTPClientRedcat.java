package mx.com.prosa.nabhi.dash.redcat.ftpconnection;

import mx.com.prosa.nabhi.misc.exception.dash.RedcatException;
import org.apache.commons.net.ftp.FTPSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static mx.com.prosa.nabhi.misc.exception.CatalogError.REDCAT_ERROR_FILE_ALREADY_EXISTS;
import static mx.com.prosa.nabhi.misc.exception.CatalogError.REDCAT_ERROR_FTP_CONNECTION_LOGIN;
import static org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE;


@Component
public class FTPClientRedcat {

    private static final Logger LOGGER = LoggerFactory.getLogger( FTPClientRedcat.class );
    private final FTPSClient ftpsClient;
    private final SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" );


    @Autowired
    public FTPClientRedcat( FTPSClient ftpsClient ) {
        this.ftpsClient = ftpsClient;
    }

    public void sendFile( ByteArrayOutputStream stream, String fiid ) throws IOException {
        InputStream inputStream = new ByteArrayInputStream( stream.toByteArray() );
        LOGGER.info( "Guardando archivo remoto: " );
        ftpsClient.enterLocalPassiveMode();
        ftpsClient.setFileType( BINARY_FILE_TYPE );
        ftpsClient.execPROT( "P" );
        String date = formatter.format( new Date() ).replace( "-", "" );
        String fileName = "REDCAT" + fiid + date + ".DAT";
        boolean isSaved = ftpsClient.storeFile( fileName, inputStream );
        if ( isSaved ) {
            LOGGER.info( "Archivo remoto almaceando con exito: " );
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( REDCAT_ERROR_FILE_ALREADY_EXISTS.getMessage() );

            }
            throw new IOException( "No se pudo guardar el archivo" );
        }

    }

    public void init( String user, String p, String host, int port ) throws IOException, RedcatException {
        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.info( "Iniciando conexión con el filesystem" );
        }
        ftpsClient.connect( host, port );
        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.info( "Conexión exitosa, iniciando login... " );
        }

        boolean login = ftpsClient.login( user, p );
        if ( login ) {
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.info( "Logueado exitoso" );
            }
        } else {
            if ( LOGGER.isErrorEnabled() ){
                LOGGER.error( REDCAT_ERROR_FTP_CONNECTION_LOGIN.getMessage() );
            }
            throw new RedcatException( REDCAT_ERROR_FTP_CONNECTION_LOGIN );
        }
    }

    public void close() throws IOException {
        LOGGER.info( "Cerrando conexión con el filesystem" );
        ftpsClient.logout();
        ftpsClient.disconnect();
    }

}
