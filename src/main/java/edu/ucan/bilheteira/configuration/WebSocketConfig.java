package edu.ucan.bilheteira.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public void registerStompEndpoints (StompEndpointRegistry registry){
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:4200").withSockJS()
//        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS()
//                .setInterceptors(new HttpSessionHandshakeInterceptor())
//                .set

        ;
    }

//    private HandshakeHandler defaultHandshakeHandler() {
//        return new DefaultHandshakeHandler() {
//            //@Override
//            protected boolean sameOriginDisabled() {
//                // Permitir o uso de CORS para solicitações WebSocket
//                return true;
//            }
//        };
//    }

}
