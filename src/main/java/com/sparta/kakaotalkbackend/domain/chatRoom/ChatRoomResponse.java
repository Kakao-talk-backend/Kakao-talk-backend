package com.sparta.kakaotalkbackend.domain.chatRoom;

import lombok.Getter;

@Getter
public class ChatRoomResponse {

//    private Long roomId;

    private String roomName;

    private String username;

    public ChatRoomResponse(ChatRoom chatRoom) {
//        this.roomId = chatRoom.getId();
        this.roomName = chatRoom.getRoomName();
        this.username = chatRoom.getYou();
    }

}
