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
    private PostReader postReader;
    private UserReader userReader;
    private UserRepository userRepository;


    @Transactional
    public Post createPost(Long userId, PostRequestDto postRequestDto) {
        User user = userReader.findById(userId)
            .orElseThrow(RuntimeException::new);

        Post post = PostMapper.toEntity(user, postRequestDto);

        return postRepository.save(post);
    }

    @Transactional
    public void updatePost(Long postId, Long userId, PostRequestDto postRequestDto) {
        Post post = postReader.findByIdAndUserId(postId, userId)
            .orElseThrow(RuntimeException::new);

        post.update(postRequestDto.getContent());
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postReader.findByIdAndUserId(postId, userId)
            .orElseThrow(RuntimeException::new);

        postRepository.delete(post);
    }
}
