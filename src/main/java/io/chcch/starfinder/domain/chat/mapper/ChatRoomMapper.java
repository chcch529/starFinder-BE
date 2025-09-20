package io.chcch.starfinder.domain.chat.mapper;

import io.chcch.starfinder.domain.chat.entity.ChatRoom;
import io.chcch.starfinder.domain.user.entity.User;

public class ChatRoomMapper {

    public static ChatRoom toEntity(User smallUser, User largeUser) {
        return ChatRoom.create(smallUser, largeUser);
    }
}
