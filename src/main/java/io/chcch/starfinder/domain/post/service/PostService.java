package io.chcch.starfinder.domain.post.service;

import io.chcch.starfinder.domain.post.dao.PostRepository;
import io.chcch.starfinder.domain.post.dto.PostListResponse;
import io.chcch.starfinder.domain.post.dto.PostRequestDto;
import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.post.mapper.PostMapper;
import io.chcch.starfinder.domain.user.dao.UserRepository;
import io.chcch.starfinder.domain.user.entity.User;
import io.chcch.starfinder.domain.user.service.UserReader;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

    @Transactional(readOnly = true)
    public Slice<PostListResponse> getPosts(Long cursor, int size, Long userId) {
        User user = userReader.findById(userId)
            .orElseThrow(RuntimeException::new);

        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, size + 1, sort);
        List<PostListResponse> nextPage = postReader.findNextPage(cursor, pageable);

        boolean hasNext = nextPage.size() > size;
        List<PostListResponse> content = hasNext
            ? nextPage.subList(0, size)
            : nextPage;

        return new SliceImpl<>(content, PageRequest.of(0, size, sort), hasNext);

    }
}
