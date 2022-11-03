package com.sparta.kakaotalkbackend.domain.friend;

import com.sparta.kakaotalkbackend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // myUsername = 현재 로그인된 사람의 username / username = 친구의(상대방의) username
    @Column
    private String myUsername;

    @Column
    private String username;

    @Column
    private String nickname;

    @Column
    private String image;

    @Column
    private String status;

}
