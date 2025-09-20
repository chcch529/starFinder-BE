package io.chcch.starfinder.domain.post.dto;

import java.time.LocalDateTime;

public record PostResponseDto(
    Long id,

    String content,

    Long writerId,

    String nickname,

    String profileImg,

    LocalDateTime createdAt,

    Long likeCnt,

    Long CommentCnt,

    Long userId

) {

}
