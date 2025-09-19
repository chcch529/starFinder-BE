package io.chcch.starfinder.domain.post.dao;

import io.chcch.starfinder.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {



}
