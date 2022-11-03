package com.sparta.kakaotalkbackend.domain.friend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FriendResponseDto {
        private Long id;
        private String myUsername;
        private String username;
        private String nickname;
        private String image;
        private String status;
        private boolean isRoomOpen;
}
