package io.chcch.starfinder.domain.chat.entity;

import io.chcch.starfinder.domain.global.entity.BaseDateEntity;
import io.chcch.starfinder.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "chat_rooms")
public class ChatRoom extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "small_user_id")
    private User smallUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "large_user_id")
    private User largeUser;

    public static ChatRoom create(User smallUser, User largeUser) {
        return ChatRoom.builder()
            .smallUser(smallUser)
            .largeUser(largeUser)
            .build();
    }
}
