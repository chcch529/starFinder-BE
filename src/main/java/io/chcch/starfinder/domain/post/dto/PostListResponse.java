package io.chcch.starfinder.domain.post.dto;

import java.time.LocalDateTime;

public record PostListResponse(

    Long id,

    String content,

    String nickname,

    String profileImg,

    LocalDateTime createdAt,

    Long likeCnt,

    Long CommentCnt,

    Long userId
) {

}
