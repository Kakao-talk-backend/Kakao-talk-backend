package com.sparta.kakaotalkbackend.service;

import com.sparta.kakaotalkbackend.domain.ResponseDto;
import com.sparta.kakaotalkbackend.domain.chatRoom.ChatRoom;
import com.sparta.kakaotalkbackend.domain.chatRoom.ChatRoomRequest;
import com.sparta.kakaotalkbackend.domain.chatRoom.ChatRoomResponse;
import com.sparta.kakaotalkbackend.domain.friend.Friend;
import com.sparta.kakaotalkbackend.domain.member.Member;
import com.sparta.kakaotalkbackend.exception.ErrorCode;
import com.sparta.kakaotalkbackend.exception.GlobalException;
import com.sparta.kakaotalkbackend.repository.ChatRoomRepository;
import com.sparta.kakaotalkbackend.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final FriendRepository friendRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ResponseDto<ChatRoomResponse> createChatRoom(Member member, Long friendId, @Valid ChatRoomRequest chatRoomRequest) {
        Optional<Friend> friend = friendRepository.findById(friendId);
        if (friend.isEmpty()) throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);


        // 방 이미 있는경우 없는 경우 나누기
//        String roomId = UUID.randomUUID().toString();
        String roomName = chatRoomRequest.getRoomName();
        ChatRoom chatRoom = new ChatRoom(roomName, member.getUsername(), friend.get().getUsername());
        chatRoomRepository.save(chatRoom);

        return ResponseDto.success(new ChatRoomResponse(chatRoom));
    }

    public ResponseDto<List<ChatRoomResponse>> getRooms(Member member) {
        List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByMe(member.getUsername());

        List<ChatRoomResponse> chatRoomResponses = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {
            chatRoomResponses.add(new ChatRoomResponse(chatRoom));
        }
        return ResponseDto.success(chatRoomResponses);
    }

    public ResponseDto<ChatRoomResponse> enterChatRoom(Long roomId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(roomId);

        return ResponseDto.success(new ChatRoomResponse(chatRoom.get()));
    }
}
