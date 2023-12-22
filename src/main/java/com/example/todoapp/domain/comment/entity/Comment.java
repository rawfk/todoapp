package com.example.todoapp.domain.comment.entity;

import com.example.todoapp.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @EmbeddedId
    private CommentPK id;

    @Column(name = "comment_content", nullable = false)
    private String content;
}