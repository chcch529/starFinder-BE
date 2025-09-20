package io.chcch.starfinder.domain.post.mapper;

import io.chcch.starfinder.domain.post.dto.PostRequestDto;
import io.chcch.starfinder.domain.post.dto.PostResponseDto;
import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.user.entity.User;

public class PostMapper {

    public static Post toEntity(User user, PostRequestDto postRequestDto) {
        return Post.create(user, postRequestDto.getContent());
    }

    public static PostResponseDto toPostResponseDto(Post post, Long userId, Long likeCnt, Long commentCnt) {
        User user = post.getUser();

        return new PostResponseDto(
            post.getId(),
            post.getContent(),
            userId,
            user.getNickname(),
            user.getProfileImg(),
            post.getCreatedAt(),
            likeCnt,
            commentCnt
        );
    }
}
