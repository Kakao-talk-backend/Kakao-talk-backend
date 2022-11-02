package com.sparta.kakaotalkbackend.domain.chatRoom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    private String roomId;

    @Column
    private String roomName;

    public static ChatRoom create(String roomName) {
        ChatRoom room = new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = roomName;
        return room;
    }
}
