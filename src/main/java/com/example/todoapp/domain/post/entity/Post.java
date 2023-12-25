package com.example.todoapp.domain.post.entity;

import com.example.todoapp.domain.comment.entity.Comment;
import com.example.todoapp.domain.post.dto.PostUpdateDto;
import com.example.todoapp.domain.postLike.entity.PostLike;
import com.example.todoapp.global.entity.BaseTimeEntity;
import com.example.todoapp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter @Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(name = "post_content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "id.post", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikes = new ArrayList<>();

    @Builder
    public Post(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void updatePost(PostUpdateDto updateDto) {
        this.title = updateDto.getTitle();
        this.content = updateDto.getContent();
    }
}