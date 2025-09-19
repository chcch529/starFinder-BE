package io.chcch.starfinder.domain.post.service;

import io.chcch.starfinder.domain.post.dao.PostRepository;
import io.chcch.starfinder.domain.post.dto.PostRequestDto;
import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.post.mapper.PostMapper;
import io.chcch.starfinder.domain.user.dao.UserRepository;
import io.chcch.starfinder.domain.user.entity.User;
import io.chcch.starfinder.domain.user.service.UserReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserReader userReader;
    private UserRepository userRepository;


    @Transactional
    public Post createPost(Long userId, PostRequestDto postRequestDto) {
        User user = userReader.findById(userId)
            .orElseThrow(RuntimeException::new);

        Post post = PostMapper.toEntity(user, postRequestDto);

        return postRepository.save(post);
    }

}
