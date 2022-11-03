package com.sparta.kakaotalkbackend.service;

import com.sparta.kakaotalkbackend.domain.ResponseDto;
import com.sparta.kakaotalkbackend.domain.chat.ChatMessage;
import com.sparta.kakaotalkbackend.domain.chat.ChatMessageRequestDto;
import com.sparta.kakaotalkbackend.domain.chat.ChatMessageResponseDto;
import com.sparta.kakaotalkbackend.domain.chatRoom.ChatRoom;
import com.sparta.kakaotalkbackend.domain.friend.Friend;
import com.sparta.kakaotalkbackend.domain.member.Member;
import com.sparta.kakaotalkbackend.domain.member.MemberResponseDto;
import com.sparta.kakaotalkbackend.jwt.JwtProvider;
import com.sparta.kakaotalkbackend.jwt.UserDetailsImpl;
import com.sparta.kakaotalkbackend.repository.ChatMessageRepository;
import com.sparta.kakaotalkbackend.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final JwtProvider jwtProvider;
    private final ChatRoomRepository chatRoomRepository;

    // 채팅 생성
    public ResponseDto<ChatMessageResponseDto> createChatMessage(ChatMessageRequestDto requestDto, Long roomId, String token) {
        Authentication authentication = jwtProvider.getAuthentication(token);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        Member member = userDetails.getMember();

        ChatMessage chatMessage = ChatMessage.builder()
                .message(requestDto.getMessage())
                .roomId(chatRoom.getId())
                .build();

        chatMessageRepository.save(chatMessage);

        return ResponseDto.success(ChatMessageResponseDto.builder()
                .message(chatMessage.getMessage())
                .nickname(member.getNickname())
                .build());
    }

    // 채팅 목록 조회
    public ResponseDto<List<ChatMessageResponseDto>> getAllMessages(Long roomId) {
        List<ChatMessage> messageList = chatMessageRepository.findAllByRoomId(roomId);
        List<ChatMessageResponseDto> responseList = new ArrayList<>();

        for (ChatMessage chatMessage : messageList) {
            responseList.add(
                    ChatMessageResponseDto.builder()
                            .message(chatMessage.getMessage())
                            .build()
            );

        }
        return ResponseDto.success(responseList);
    }

//    public ResponseDto<List<MemberResponseDto>> getAllFriends(Member member) {
//        // friendRepository 에서 나의이름을 기준으로 찾기 (나와 연결된 친구를 찾기 위해)
//        List<Friend> findFriendList = friendRepository.findAllByMyUsername(member.getUsername());
//        List<MemberResponseDto> friendList = new ArrayList<>();
//
//        for (Friend friend : findFriendList) {
//            friendList.add(
//                    MemberResponseDto.builder()
//                            .id((friend.getId()))
//                            .username(friend.getUsername())
//                            .nickname(friend.getNickname())
//                            .image(friend.getImage())
//                            .status(friend.getStatus())
//                            .build()
//            );
//        }
//        return ResponseDto.success(friendList);
//    }

}
