package com.sparta.kakaotalkbackend.domain.chat;

import com.sparta.kakaotalkbackend.domain.chatRoom.ChatRoom;
import com.sparta.kakaotalkbackend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Builder
@AllArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String message;

    @Column
    private Long roomId;

//    @ManyToOne
//    @JoinColumn
//    private Member member;
//
//    @ManyToOne
//    @JoinColumn
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private ChatRoom chatRoom;
}
