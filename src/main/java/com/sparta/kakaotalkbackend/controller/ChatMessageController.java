package com.sparta.kakaotalkbackend.controller;

import com.sparta.kakaotalkbackend.domain.ResponseDto;
import com.sparta.kakaotalkbackend.domain.chat.ChatMessage;
import com.sparta.kakaotalkbackend.domain.chat.ChatMessageRequestDto;
import com.sparta.kakaotalkbackend.domain.chat.ChatMessageResponseDto;
import com.sparta.kakaotalkbackend.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatMessageController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageService messageService;

    @MessageMapping("/rooms/{roomId}/chattings")
    public void createChatMessage(@RequestBody ChatMessageRequestDto requestDto, @DestinationVariable Long roomId,
                                  @Header("Authorization") String token) {
        token = token.substring(7);
        ChatMessageResponseDto responseDto = messageService.createChatMessage(requestDto, roomId, token).getData();
        messagingTemplate.convertAndSend("/sub/chat/room/" + roomId, responseDto);
    }

    @ResponseBody
    @GetMapping("/rooms/{roomId}/chattings")
    public ResponseDto<List<ChatMessageResponseDto>> getMessages(@PathVariable Long roomId) {

        return messageService.getAllMessages(roomId);
    }
}
