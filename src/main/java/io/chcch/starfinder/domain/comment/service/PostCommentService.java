package io.chcch.starfinder.domain.comment.service;

import io.chcch.starfinder.domain.comment.dao.PostCommentRepository;
import io.chcch.starfinder.domain.comment.dto.CommentResponseDto;
import io.chcch.starfinder.domain.comment.dto.PostCommentResponseDto;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostCommentService {

    private PostCommentRepository postCommentRepository;
    private PostCommentReader postCommentReader;

    @Transactional(readOnly = true)
    public List<PostCommentResponseDto> getComments(Long postId) {
        // 대댓글 수 미포함 댓글 리스트 조회
        List<CommentResponseDto> comments = postCommentReader.findCommentsByPostId(postId);
        if (comments.isEmpty()) return Collections.emptyList();

        // 댓글 id 추출
        List<Long> parentIds = comments.stream()
            .map(CommentResponseDto::id)
            .toList();

        // 대댓글 수 카운트
        List<Object[]> objects = postCommentReader.countReCommentByParentIds(parentIds);

        // map 변환
        Map<Long, Long> reCommentCnt = objects.stream()
            .collect(Collectors.toMap(
                arr -> ((Number) arr[0]).longValue(),
                arr -> ((Number) arr[1]).longValue()
            ));

        // 최종 dto
        return comments.stream()
            .map(c -> new PostCommentResponseDto(
                c.id(),
                c.nickname(),
                c.profileImg(),
                c.content(),
                c.createdAt(),
                reCommentCnt.getOrDefault(c.id(), 0L)
            ))
            .toList();
    }

}
