package io.chcch.starfinder.domain.comment.dao;

import io.chcch.starfinder.domain.comment.dto.CommentResponseDto;
import io.chcch.starfinder.domain.comment.entity.PostComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    // 대댓글 수 미포함
    @Query("""
        SELECT new io.chcch.starfinder.domain.comment.dto.CommentResponseDto(
            c.id,
            c.user.nickname,
            c.user.profileImg,
            c.content,
            c.createdAt
        )
        FROM PostComment c
        WHERE c.post.id = :postId
        ORDER BY c.createdAt ASC
        """)
    List<CommentResponseDto> findCommentsByPostId(@Param("postId") Long postId);

    // 대댓글 수 포함
    @Query("""
        SELECT c.parentComment.id, COUNT(c)
        FROM PostComment c
        WHERE c.parentComment.id IN :parentIds
        GROUP BY c.parentComment.id
        """)
    List<Object[]> countReCommentByParentIds(
        @Param("parentIds") List<Long> parentIds);

}
