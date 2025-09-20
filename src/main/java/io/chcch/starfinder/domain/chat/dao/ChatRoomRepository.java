package io.chcch.starfinder.domain.chat.dao;

import io.chcch.starfinder.domain.chat.entity.ChatRoom;
import io.chcch.starfinder.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("""
            SELECT c
            FROM ChatRoom c
            WHERE c.smallUser.id = :smallId
            AND c.largeUser.id = :largeId
        """)
    Optional<ChatRoom> findByUserIds(Long smallId, Long largeId);

    @Query("""
            SELECT c
            FROM ChatRoom c
            WHERE c.smallUser = :smallUser
            AND c.largeUser = :largeUser
        """)
    Optional<ChatRoom> findByUsers(User smallUser, User largeUser);
}
