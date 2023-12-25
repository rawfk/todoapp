package com.example.todoapp.domain.commentLike.controller;

import com.example.todoapp.domain.commentLike.service.CommentLikeService;
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
@RequestMapping("/api/posts/{postId}/comments/{commentId}/likes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping
    public ResponseEntity<CustomResponse> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails , @PathVariable Long postId, @PathVariable Long commentId) {
        if (commentLikeService.like(userDetails, postId, commentId)) {
            return CustomResponse.toResponseEntity(StatusEnum.COMMENT_LIKE_SUCCESS);
        }
        return CustomResponse.toResponseEntity(StatusEnum.COMMENT_LIKE_CANCEL);
    }
}