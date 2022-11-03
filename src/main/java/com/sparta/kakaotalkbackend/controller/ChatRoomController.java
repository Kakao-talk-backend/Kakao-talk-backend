package com.sparta.kakaotalkbackend.controller;

import com.sparta.kakaotalkbackend.domain.ResponseDto;
import com.sparta.kakaotalkbackend.domain.chatRoom.ChatRoomRequest;
import com.sparta.kakaotalkbackend.domain.chatRoom.ChatRoomResponse;
import com.sparta.kakaotalkbackend.jwt.UserDetailsImpl;
import com.sparta.kakaotalkbackend.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/friends/{friendId}")
    public ResponseDto<ChatRoomResponse> createChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long friendId,
                                                        @RequestBody @Valid ChatRoomRequest chatRoomRequest) {
        return chatRoomService.createChatRoom(userDetails.getMember(), friendId, chatRoomRequest);
    }

    @GetMapping("/rooms")
    public ResponseDto<List<ChatRoomResponse>> getRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.getRooms(userDetails.getMember());
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseDto<ChatRoomResponse> enterChatRoom(@PathVariable Long roomId) {
        return chatRoomService.enterChatRoom(roomId);
    }
}
