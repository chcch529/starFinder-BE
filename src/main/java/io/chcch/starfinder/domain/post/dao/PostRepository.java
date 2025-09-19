package io.chcch.starfinder.domain.post.dao;

import io.chcch.starfinder.domain.post.dto.PostListResponse;
import io.chcch.starfinder.domain.post.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndUserId(Long id, Long userId);

    @Query("""
        SELECT new io.chcch.starfinder.domain.post.dto.PostListResponse(
            p.id,
            p.content,
            u.nickname,
            u.profileImg,
            (SELECT COUNT(l) + 0L FROM PostLike l WHERE l.post = p),
            (SELECT COUNT(c) + 0L FROM PostComment c WHERE c.post = p),
            u.id
        )
        FROM Post p
        JOIN p.user u
        WHERE (:cursor IS NULL OR p.id < :cursor)
        ORDER BY p.id DESC
    """)
    List<PostListResponse> findNextPage(@Param("cursor") Long cursor, Pageable pageable);

}
