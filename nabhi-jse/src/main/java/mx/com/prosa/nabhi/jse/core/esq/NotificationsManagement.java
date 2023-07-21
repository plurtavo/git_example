package mx.com.prosa.nabhi.jse.core.esq;

import mx.com.prosa.nabhi.misc.exception.rest.RestExchangeException;
import mx.com.prosa.nabhi.misc.model.esq.XFSMessage;
import mx.com.prosa.nabhi.misc.rest.esq.ESQRequester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class NotificationsManagement {

    private final ESQRequester esqRequester;
    private static final Logger LOGGER = LoggerFactory.getLogger( NotificationsManagement.class );

    @Value( "${esq.url}" )
    private String url;

    @Autowired
    public NotificationsManagement( ESQRequester esqRequester ) {
        this.esqRequester = esqRequester;
    }

    @Async
    public void sendEvent( String termID, int atmCode ) {
        try {
            esqRequester.exchange( new XFSMessage( termID, atmCode ), url );
        } catch ( RestExchangeException e ) {
            LOGGER.error( e.getMessage() );
        }
    }
}
