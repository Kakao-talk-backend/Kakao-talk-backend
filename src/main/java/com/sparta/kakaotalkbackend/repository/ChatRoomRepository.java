package com.sparta.kakaotalkbackend.repository;

import com.sparta.kakaotalkbackend.domain.chatRoom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findChatRoomsByMe(String username);

    Optional<ChatRoom> findChatRoomByMeAndYou(String me, String you);
}
