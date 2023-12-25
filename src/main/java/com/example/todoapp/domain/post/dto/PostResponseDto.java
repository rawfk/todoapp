package com.example.todoapp.domain.post.dto;

import com.example.todoapp.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

    private String title;
    private String nickname;
    private String content;
    private ZonedDateTime createdDate;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
    }
}
