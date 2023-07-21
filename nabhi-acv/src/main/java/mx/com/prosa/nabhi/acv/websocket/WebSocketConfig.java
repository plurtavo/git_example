package mx.com.prosa.nabhi.acv.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import mx.com.prosa.nabhi.acv.websocket.business.IWebSocketService;
import mx.com.prosa.nabhi.misc.domain.acv.entity.ATMLogConectionEntity;
import mx.com.prosa.nabhi.misc.domain.acv.repository.ATMLogConnectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import java.sql.Timestamp;
import java.util.Optional;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer implements WebSocketMessageBrokerConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger( WebSocketConfig.class );
    private final ATMLogConnectionRepository atmLogConnectionRepository;
    private final IWebSocketService webSocketService;

    @Autowired
    public WebSocketConfig( ATMLogConnectionRepository atmLogConnectionRepository, IWebSocketService webSocketService ) {
        this.atmLogConnectionRepository = atmLogConnectionRepository;
        this.webSocketService = webSocketService;
    }

    @Override
    public void configureWebSocketTransport( WebSocketTransportRegistration registration ) {
        registration.addDecoratorFactory( new WebSocketHandlerAdapter( atmLogConnectionRepository, webSocketService ) );
    }

    @Override
    protected void configureInbound( MessageSecurityMetadataSourceRegistry messages ) {
        messages.nullDestMatcher().authenticated()
                .simpDestMatchers( "/**" ).authenticated()
                .anyMessage().denyAll();
    }

    @Override
    public void configureMessageBroker( MessageBrokerRegistry config ) {
        config.enableSimpleBroker( "/topic" );
        config.setApplicationDestinationPrefixes( "/app" );
    }

    @Override
    public void registerStompEndpoints( StompEndpointRegistry registry ) {
        registry.addEndpoint( "/acv-ws" ).setAllowedOrigins( "*" ).withSockJS();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }


    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure( SerializationFeature.FAIL_ON_EMPTY_BEANS, false );
        return new MappingJackson2HttpMessageConverter( mapper );
    }

    private static class WebSocketHandlerAdapter implements WebSocketHandlerDecoratorFactory {

        private final ATMLogConnectionRepository atmLogConnectionRepository;
        private final IWebSocketService webSocketService;

        public WebSocketHandlerAdapter( ATMLogConnectionRepository atmLogConnectionRepository, IWebSocketService webSocketService ) {
            this.atmLogConnectionRepository = atmLogConnectionRepository;
            this.webSocketService = webSocketService;
        }

        @Override
        public WebSocketHandler decorate( WebSocketHandler webSocketHandler  ) {
            return new WebSocketHandlerDecorator( webSocketHandler ) {
                @Override
                public void afterConnectionEstablished( final WebSocketSession session ) throws Exception {
                    if ( LOG.isInfoEnabled() ) {
                        LOG.info( "WebSocket Client connected: " );
                        ATMLogConectionEntity entity = new ATMLogConectionEntity();
                        entity.setSessionId( session.getId() );
                        entity.setUp( new Timestamp( System.currentTimeMillis() ) );
                        atmLogConnectionRepository.save( entity );
                        LOG.info( session.getId() );
                    }
                    super.afterConnectionEstablished( session );
                }

                @Override
                public void afterConnectionClosed( WebSocketSession session, CloseStatus closeStatus ) throws Exception {
                    if ( LOG.isInfoEnabled() ) {
                        LOG.info( "WebSocket Client disconnected: " );
                        LOG.info( session.getId() );
                        Optional < ATMLogConectionEntity > optional = atmLogConnectionRepository.findAllBySessionId( session.getId() );
                        if ( optional.isPresent() ){
                            ATMLogConectionEntity entity = optional.get();
                            entity.setSessionId( session.getId() );
                            entity.setDown( new Timestamp( System.currentTimeMillis() ) );
                            atmLogConnectionRepository.save( entity );
                            webSocketService.onDown( session.getId() );
                        }
                    }
                    super.afterConnectionClosed( session, closeStatus );
                }
            };
        }
    }
}


