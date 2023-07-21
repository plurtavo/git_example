package mx.com.prosa.nabhi.esq.bussines.impl;

import mx.com.prosa.nabhi.esq.bussines.IESQService;
import mx.com.prosa.nabhi.esq.data.XFSEntity;
import mx.com.prosa.nabhi.esq.data.XFSToDDC;
import mx.com.prosa.nabhi.esq.exception.SocketException;
import mx.com.prosa.nabhi.esq.exception.XFSException;
import mx.com.prosa.nabhi.esq.model.Response;
import mx.com.prosa.nabhi.esq.model.XFSMessage;
import mx.com.prosa.nabhi.esq.socket.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class ESQService implements IESQService {

    private static final Logger LOG = LoggerFactory.getLogger( ESQService.class );
    private final MessageHandler messageHandler;

    @Autowired
    public ESQService( MessageHandler messageHandler ) {
        this.messageHandler = messageHandler;
    }

    @Override
    public Response xfs( XFSMessage xfsMessage ) {
        Response response = new Response();
        try {
            XFSEntity entity = XFSToDDC.searchByXFSCode( xfsMessage.getCodigoXFS() );
            String nemotecnico = xfsMessage.getNemoTecnico();
            String ddcCode = String.format( ( "%0" + 3 + "d" ), entity.getDdcCode() );
            String mensajeEnviar = ddcCode + "  " + nemotecnico + " " + entity.getDdcDescription() + " " + entity.getModule() + " " + entity.getSeverity();
            messageHandler.send( mensajeEnviar );
            response.setCodResp( 0 );
            response.setEstado( HttpStatus.OK.value() );
            response.setDescripcion( "Proesado correctamente en B24" );
        } catch ( XFSException e ) {
            response.setDescripcion( "No existe el código XFS enviado" );
            response.setCodResp( 1 );
            response.setEstado( HttpStatus.NOT_FOUND.value() );
        } catch ( SocketException ex ) {
            LOG.error( ex.getMessage() );
            response.setCodResp( 1 );
            response.setEstado( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setDescripcion( "Se produjo un error en el procesamiento de la petición" );
        }
        return response;
    }
}
