package io.chcch.starfinder.domain.post.mapper;

import io.chcch.starfinder.domain.post.dto.PostRequestDto;
import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.user.entity.User;

public class PostMapper {

    public static Post toEntity(User user, PostRequestDto postRequestDto) {
        return Post.create(user, postRequestDto.getContent());
    }
}
