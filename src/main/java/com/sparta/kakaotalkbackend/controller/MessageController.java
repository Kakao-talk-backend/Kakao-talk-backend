//package com.sparta.kakaotalkbackend.controller;
//
//import com.sparta.kakaotalkbackend.domain.message.Message;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class MessageController {
//
//    // 메세지를 destination 까지 보낼 수 있도록 convertAndSend 메서드를 사용해서 특정 주소로 보냄
//    private final SimpMessageSendingOperations sendingOperations;
//
//    // 클라이언트가 메세지를 send 할 수 있는 경로
//    // applicationDestinationPrefixes 와 MessageMapping 의 경로가 합쳐짐
//    @MessageMapping("/chat/message")
//    public void enter(Message message) {
//        sendingOperations.convertAndSend("topic/chat/room/" + message.getRoomId(),message);
//    }
//}
