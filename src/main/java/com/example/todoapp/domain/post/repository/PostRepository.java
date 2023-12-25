package com.example.todoapp.domain.post.repository;

import com.example.todoapp.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
