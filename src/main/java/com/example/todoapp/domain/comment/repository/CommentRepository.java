package com.example.todoapp.domain.comment.repository;

import com.example.todoapp.domain.comment.entity.Comment;
import com.example.todoapp.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
