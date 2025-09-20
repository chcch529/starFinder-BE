package io.chcch.starfinder.domain.chat.dto;

import java.time.LocalDateTime;

public record ChatRoomListResponse(
    String receiverName,
    String receiverImg,
    String lastMessage,
    LocalDateTime lastTime,
    boolean isRead
) {

}
