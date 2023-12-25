package com.example.todoapp.domain.post.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDto {

    @Size(max = 500)
    private String title;
    @Size(max = 5000)
    private String content;
}
