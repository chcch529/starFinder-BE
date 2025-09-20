package io.chcch.starfinder.domain.comment.dto;

import java.time.LocalDateTime;

public record PostCommentResponseDto(
    Long id,
    String nickname,
    String profileImg,
    String content,
    LocalDateTime createdAt,
    Long reCommentCnt
) {

}
