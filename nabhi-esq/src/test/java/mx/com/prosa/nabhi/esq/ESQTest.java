package mx.com.prosa.nabhi.esq;

import mx.com.prosa.nabhi.esq.data.XFSToDDC;
import mx.com.prosa.nabhi.esq.model.Response;
import mx.com.prosa.nabhi.esq.model.XFSMessage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;


@SpringBootTest( classes = ESQApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@TestMethodOrder( MethodOrderer.OrderAnnotation.class )
class ESQTest {

    private static final Logger LOG = LoggerFactory.getLogger( ESQTest.class );
    private static final String HOST = "http://localhost:";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void testESQNotify() throws MalformedURLException {
        HttpHeaders header = new HttpHeaders();
        header.setAccept( Collections.singletonList( MediaType.APPLICATION_JSON ) );
        header.add( "Content-Type", "application/json" );
            LOG.info( String.format( "Send XFS Code %s", XFSToDDC.XFS_1.getXfsCode() ) );
            HttpEntity < ? > httpEntity = new HttpEntity <>( new XFSMessage( "ABCCAP", XFSToDDC.XFS_1.getXfsCode() ), header );
            ResponseEntity < Response > response = restTemplate.exchange( new URL( HOST + port + "" ).toString(), HttpMethod.POST, httpEntity, Response.class );
            if ( response.getStatusCode() == HttpStatus.OK ) {
                LOG.info( String.format( "XFS Code %s test successful", XFSToDDC.XFS_1.getXfsCode() ) );
            }
    }


    @Test
    void testESQNotifyInvalidCode() throws MalformedURLException {
        HttpHeaders header = new HttpHeaders();
        header.setAccept( Collections.singletonList( MediaType.APPLICATION_JSON ) );
        header.add( "Content-Type", "application/json" );
        LOG.info( String.format( "Send XFS Code %s", -1000 ) );
        HttpEntity < ? > httpEntity = new HttpEntity <>( new XFSMessage( "ABCCAP", -1000 ), header );
        ResponseEntity < Response > response = restTemplate.exchange( new URL( HOST + port + "" ).toString(), HttpMethod.POST, httpEntity, Response.class );
        if ( response.getStatusCode() == HttpStatus.OK ) {
            LOG.info( String.format( "XFS Code %s test successful", -1000 ) );
        }
    }

}
