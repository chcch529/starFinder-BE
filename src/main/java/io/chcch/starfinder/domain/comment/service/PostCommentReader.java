package io.chcch.starfinder.domain.comment.service;

import io.chcch.starfinder.domain.comment.dao.PostCommentRepository;
import io.chcch.starfinder.domain.comment.dto.CommentResponseDto;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PostCommentReader {

    private PostCommentRepository postCommentRepository;

    List<CommentResponseDto> findCommentsByPostId(Long postId) {
        return postCommentRepository.findCommentsByPostId(postId);
    }

    List<Object[]> countReCommentByParentIds(List<Long> parentIds) {
        return postCommentRepository.countReCommentByParentIds(parentIds);
    }

}
