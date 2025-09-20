package io.chcch.starfinder.domain.comment.dto;

import java.time.LocalDateTime;

public record CommentResponseDto(
    Long id,
    String nickname,
    String profileImg,
    String content,
    LocalDateTime createdAt

) {

}
