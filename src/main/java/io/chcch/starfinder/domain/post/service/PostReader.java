package io.chcch.starfinder.domain.post.service;

import io.chcch.starfinder.domain.post.dao.PostRepository;
import io.chcch.starfinder.domain.post.dto.PostListResponseDTo;
import io.chcch.starfinder.domain.post.dto.PostResponseDto;
import io.chcch.starfinder.domain.post.entity.Post;
import java.util.List;
import java.util.Optional;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PostReader {

    private PostRepository postRepository;

    public Optional<Post> findByIdAndUserId(Long id, Long userId) {
        return postRepository.findByIdAndUserId(id, userId);
    }

    public List<PostListResponseDTo> findNextPage(Long cursor, Pageable pageable) {
        return postRepository.findNextPage(cursor, pageable);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public PostResponseDto findPostByIdWithCounts(Long postId, Long userId) {
        return postRepository.findPostByIdWithCounts(postId, userId);
    }
}
