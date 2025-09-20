package io.chcch.starfinder.domain.chat.dao;

import io.chcch.starfinder.domain.chat.dto.ChatRoomListResponse;
import io.chcch.starfinder.domain.chat.entity.ChatRoom;
import io.chcch.starfinder.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("""
            SELECT c
            FROM ChatRoom c
            WHERE c.smallUser.id = :smallId
            AND c.largeUser.id = :largeId
        """)
    Optional<ChatRoom> findByUserIds(@Param("smallId") Long smallId, @Param("largeId") Long largeId);

    @Query("""
            SELECT c
            FROM ChatRoom c
            WHERE c.smallUser = :smallUser
            AND c.largeUser = :largeUser
        """)
    Optional<ChatRoom> findByUsers(@Param("smallUser")User smallUser, @Param("largeUser") User largeUser);

    @Query("""
        SELECT new io.chcch.starfinder.domain.chat.dto.ChatRoomListResponse(
            CASE WHEN s.id = :userId THEN l.nickname ELSE s.nickname END,
            CASE WHEN s.id = :userId THEN l.profileImg ELSE s.profileImg END,
            m.content,
            m.createdAt,
            m.isRead
        )
        FROM ChatRoom r
        JOIN r.smallUser s
        JOIN r.largeUser l
        JOIN ChatMessage m
            ON m.chatRoom = r
            AND m.createdAt = (
                SELECT MAX(cm.createdAt)
                FROM ChatMessage cm
                WHERE cm.chatRoom = r
            )
        WHERE r.largeUser.id = :userId
        OR r.smallUser.id = :userId
        ORDER BY m.createdAt DESC
        """)
    List<ChatRoomListResponse> findChatRoomsWithLastMessage(@Param("userId") Long userId);
}
