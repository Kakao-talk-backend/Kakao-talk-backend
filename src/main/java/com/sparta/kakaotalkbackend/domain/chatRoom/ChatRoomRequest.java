package com.sparta.kakaotalkbackend.domain.chatRoom;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ChatRoomRequest {

    @NotBlank
    private String roomName;
}

