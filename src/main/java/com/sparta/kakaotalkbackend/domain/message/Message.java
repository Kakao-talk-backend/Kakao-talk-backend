package com.sparta.kakaotalkbackend.domain.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    // 하나의 채팅방 내에 있는 메세지라는 것만 확인하면 됨
    @Id
    private String  roomId;

    // 송신자
    @Column
    private String sender;

    // 내용
    @Column
    private String message;
}
