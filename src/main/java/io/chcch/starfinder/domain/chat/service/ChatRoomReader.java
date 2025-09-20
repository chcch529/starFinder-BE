package io.chcch.starfinder.domain.chat.service;

import io.chcch.starfinder.domain.chat.dao.ChatRoomRepository;
import io.chcch.starfinder.domain.chat.entity.ChatRoom;
import io.chcch.starfinder.domain.user.entity.User;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChatRoomReader {

    private ChatRoomRepository chatRoomRepository;

    public Optional<ChatRoom> findByUserIds(Long smallId, Long largeId) {
        return chatRoomRepository.findByUserIds(smallId, largeId);
    }

    public Optional<ChatRoom> findByUsers(User smallUser, User largeUser) {
        return chatRoomRepository.findByUsers(smallUser, largeUser);
    }

}
