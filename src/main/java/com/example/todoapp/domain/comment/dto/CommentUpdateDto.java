package com.example.todoapp.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateDto {

    @NotBlank(message = "댓글을 입력하세요.")
    private String content;
}
