package com.example.todoapp.domain.comment.entity;

import com.example.todoapp.domain.comment.dto.CommentUpdateDto;
import com.example.todoapp.domain.commentLike.entity.CommentLike;
import com.example.todoapp.domain.post.entity.Post;
import com.example.todoapp.domain.user.entity.User;
import com.example.todoapp.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter @Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "comment_content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "id.comment", cascade = CascadeType.REMOVE)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @Builder
    public Comment(User user, Post post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public void updateComment(CommentUpdateDto updateDto) {
        this.content = updateDto.getContent();
    }
}