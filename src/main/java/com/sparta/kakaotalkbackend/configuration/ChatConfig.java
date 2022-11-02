package com.sparta.kakaotalkbackend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // 웹소켓 브로커 활성화
public class ChatConfig implements WebSocketMessageBrokerConfigurer {
    // pub 이 보낸 메세지를 메세지브로커가 받아 sub 에게 뿌려주는 역할을 한다. 이를 구현한 것이 WebSocketMessageBrokerConfigurer

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat") // 클란이언트에서 서버로 웹소켓 요청을 할 때 핸드셰이크 하기위한 엔드포인트 설정
                .setAllowedOriginPatterns("*") // 도메인이 다른 서버에서도 접속할 수 있도록 cors 설정을 위해 모든 origin 에 대해 허용하는 설정
                .withSockJS(); // 클라이언트와의 연결은 SockJS 로 하기 때문에 SockJS 연결
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메세지 브로커가 subscribers 들에게 메세지를 전달할 주소
        registry.enableSimpleBroker("/queue", "topic");
        // 클라이언트가 서버로 메세지 보낼 때 붙여야하는 prefix 지정
        registry.setApplicationDestinationPrefixes("/app");
    }
}
