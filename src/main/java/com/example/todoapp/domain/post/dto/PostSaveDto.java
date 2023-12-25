package com.example.todoapp.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostSaveDto {

    @Size(max = 500)
    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @Size(max = 5000)
    @NotBlank(message = "내용을 입력하세요.")
    private String content;
}
