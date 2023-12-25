package com.example.todoapp.domain.commentLike.repository;

import com.example.todoapp.domain.commentLike.entity.CommentLike;
import com.example.todoapp.domain.commentLike.entity.CommentLikePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikePK> {
}
