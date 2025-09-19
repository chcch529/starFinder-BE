package io.chcch.starfinder.domain.post.service;

import io.chcch.starfinder.domain.post.dao.PostRepository;
import io.chcch.starfinder.domain.post.entity.Post;
import java.util.Optional;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PostReader {

    private PostRepository postRepository;

    public Optional<Post> findByIdAndUserId(Long id, Long userId) {
        return postRepository.findByIdAndUserId(id, userId);
    }

}
