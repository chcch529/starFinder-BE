package io.chcch.starfinder.domain.chat.service;

import io.chcch.starfinder.domain.chat.dao.ChatRoomRepository;
import io.chcch.starfinder.domain.chat.dto.ChatRoomListResponse;
import io.chcch.starfinder.domain.chat.entity.ChatRoom;
import io.chcch.starfinder.domain.chat.mapper.ChatRoomMapper;
import io.chcch.starfinder.domain.user.entity.User;
import io.chcch.starfinder.domain.user.service.UserReader;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatRoomService {

    private ChatRoomRepository chatRoomRepository;
    private ChatRoomReader chatRoomReader;
    private UserReader userReader;

    @Transactional
    public ChatRoom createChatRoom(Long userId, Long targetId) {
        long smallUserId = Math.min(userId, targetId);
        long largeUserId = Math.max(userId, targetId);

        User smallUser = userReader.findById(smallUserId)
            .orElseThrow(RuntimeException::new);
        User largeUser = userReader.findById(largeUserId)
            .orElseThrow(RuntimeException::new);

        chatRoomReader.findByUsers(smallUser, largeUser)
            .ifPresent(chatRoom -> {
                throw new RuntimeException();});

        ChatRoom chatRoom = ChatRoomMapper.toEntity(smallUser, largeUser);

        return chatRoomRepository.save(chatRoom);
    }

    @Transactional(readOnly = true)
    public Optional<ChatRoom> joinChatRoom(Long userId, Long targetId) {
        long smallUserId = Math.min(userId, targetId);
        long largeUserId = Math.max(userId, targetId);

        return chatRoomReader.findByUserIds(smallUserId, largeUserId);
    }

    @Transactional
    public ChatRoom joinOrCreateChatRoom(Long userId, Long targetId) {
        long smallUserId = Math.min(userId, targetId);
        long largeUserId = Math.max(userId, targetId);

        return joinChatRoom(smallUserId, largeUserId)
            .orElse(createChatRoom(userId, targetId));
    }

    @Transactional(readOnly = true)
    public List<ChatRoomListResponse> getChatRooms(Long userId) {
        userReader.findById(userId)
            .orElseThrow(RuntimeException::new);

        return chatRoomReader.findChatRoomsWithLastMessage(userId);
    }

}
