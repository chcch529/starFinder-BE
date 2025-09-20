package io.chcch.starfinder.domain.chat.service;

import io.chcch.starfinder.domain.chat.dao.ChatRoomRepository;
import io.chcch.starfinder.domain.chat.entity.ChatRoom;
import io.chcch.starfinder.domain.chat.mapper.ChatRoomMapper;
import io.chcch.starfinder.domain.post.service.PostService;
import io.chcch.starfinder.domain.user.entity.User;
import io.chcch.starfinder.domain.user.service.UserReader;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {

    private ChatRoomRepository chatRoomRepository;
    private ChatRoomReader chatRoomReader;
    private PostService postService;
    private UserReader userReader;

    public ChatRoom createChatRoom(Long userId, Long targetId) {
        long smallUserId = Math.min(userId, targetId);
        long largeUserId = Math.max(userId, targetId);

        User smallUser = userReader.findById(smallUserId)
            .orElseThrow(RuntimeException::new);
        User largeUser = userReader.findById(largeUserId)
            .orElseThrow(RuntimeException::new);

        ChatRoom chatRoom = ChatRoomMapper.toEntity(smallUser, largeUser);

        return chatRoomRepository.save(chatRoom);
    }



}
