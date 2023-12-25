package com.example.todoapp.domain.comment.dto;

import com.example.todoapp.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private String content;
    private ZonedDateTime createdDate;

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
    }
}
