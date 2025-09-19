package io.chcch.starfinder.domain.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import io.chcch.starfinder.domain.post.dao.PostRepository;
import io.chcch.starfinder.domain.post.dto.PostListResponse;
import io.chcch.starfinder.domain.post.dto.PostRequestDto;
import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.user.entity.User;
import io.chcch.starfinder.domain.user.service.UserReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@ExtendWith(MockitoExtension.class)
class PostServiceTests {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostReader postReader;

    @Mock
    private UserReader userReader;

    @InjectMocks
    private PostService postService;

    private User user;
    private Post post;

    @BeforeEach
    void setUp() {

        user = User.builder().build();
        post = Post.builder().build();

    }

    @Test
    @DisplayName("게시글 생성 성공")
    void post_create_success() {
        // given
        PostRequestDto postRequestDto = new PostRequestDto("new post");
        given(userReader.findById(1L)).willReturn(Optional.of(user));
        given(postRepository.save(any(Post.class))).willReturn(post);

        // when
        Post result = postService.createPost(1L, postRequestDto);

        // then
        assertThat(result).isNotNull();
        verify(postRepository).save(any(Post.class));
    }

    @Test
    @DisplayName("게시글 생성 실패 - 유저 없음")
    void post_create_fail_user() {
        // given
        PostRequestDto postRequestDto = new PostRequestDto("new Post");
        given(userReader.findById(1L)).willReturn(Optional.empty());

        // when then
        assertThrows(RuntimeException.class, () -> postService.createPost(1L, postRequestDto));

    }

    @Test
    @DisplayName("게시글 수정 성공")
    void post_update_success() {
        // given
        PostRequestDto postRequestDto = new PostRequestDto("update post");
        given(postReader.findByIdAndUserId(1L, 1L)).willReturn(Optional.of(post));

        // when
        postService.updatePost(1L, 1L, postRequestDto);

        // then
        verify(postReader).findByIdAndUserId(1L, 1L);
    }

    @Test
    @DisplayName("게시글 수정 실패 - 해당 게시글 없음")
    void post_update_fail_post() {
        // given
        PostRequestDto postRequestDto = new PostRequestDto("update post");
        given(postReader.findByIdAndUserId(1L, 1L)).willReturn(Optional.empty());

        // when then
        assertThrows(RuntimeException.class, () -> postService.updatePost(1L, 1L, postRequestDto));
    }

    @Test
    @DisplayName("게시글 삭제 성공")
    void post_delete_success() {
        // given
        given(postReader.findByIdAndUserId(1L, 1L)).willReturn(Optional.of(post));

        // when
        postService.deletePost(1L, 1L);

        // then
        verify(postRepository).delete(any(Post.class));
    }

    @Test
    @DisplayName("게시글 조회 성공")
    void post_get_success() {
        // given
        given(userReader.findById(1L)).willReturn(Optional.of(user));

        PostListResponse post1 = new PostListResponse(1L, "post1", "nickname1",
            "profile1", LocalDateTime.now(), 0L, 0L, 1L);
        PostListResponse post2 = new PostListResponse(2L, "post2", "nickname2",
            "profile2", LocalDateTime.now(), 0L, 0L, 1L);

        List<PostListResponse> postList = List.of(post1, post2);

        given(postReader.findNextPage(eq(null), any(PageRequest.class))).willReturn(postList);

        // when
        Slice<PostListResponse> slice = postService.getPosts(null, 1, 1L);

        // then
        assertThat(slice.getContent()).hasSize(1);
        assertThat(slice.hasNext()).isTrue();
    }
}