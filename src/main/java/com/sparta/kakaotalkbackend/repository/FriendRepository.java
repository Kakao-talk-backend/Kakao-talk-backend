package com.sparta.kakaotalkbackend.repository;

import com.sparta.kakaotalkbackend.domain.friend.Friend;
import com.sparta.kakaotalkbackend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findAllByMyUsername(String myname);

    Optional<Friend> findByUsernameAndMyUsername(String username, String myUsername);
}
