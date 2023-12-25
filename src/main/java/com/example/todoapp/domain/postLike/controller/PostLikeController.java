package com.example.todoapp.domain.postLike.controller;

import com.example.todoapp.domain.postLike.service.PostLikeService;
import com.example.todoapp.global.response.CustomResponse;
import com.example.todoapp.global.response.StatusEnum;
import com.example.todoapp.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<CustomResponse> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails , @PathVariable Long postId) {
        if (postLikeService.like(userDetails, postId)) {
            return CustomResponse.toResponseEntity(StatusEnum.POST_LIKE_SUCCESS);
        }
        return CustomResponse.toResponseEntity(StatusEnum.POST_LIKE_CANCEL);
    }
}