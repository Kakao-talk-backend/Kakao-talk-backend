package com.sparta.kakaotalkbackend.domain.chatRoom;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roomName;

    @Column
    private String me;

    @Column
    private String you;
//
//    public static ChatRoom create(String roomName) {
//        ChatRoom room = new ChatRoom();
//        room.roomId = UUID.randomUUID().toString();
//        room.roomName = roomName;
//        return room;
//    }

    public ChatRoom(String roomName, String me, String you) {
        this.roomName = roomName;
        this.me = me;
        this.you = you;
    }

}
