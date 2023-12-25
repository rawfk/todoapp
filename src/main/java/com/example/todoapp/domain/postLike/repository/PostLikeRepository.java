package com.example.todoapp.domain.postLike.repository;

import com.example.todoapp.domain.postLike.entity.PostLike;
import com.example.todoapp.domain.postLike.entity.PostLikePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikePK> {
}
