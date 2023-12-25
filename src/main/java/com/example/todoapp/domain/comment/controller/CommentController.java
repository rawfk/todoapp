package com.example.todoapp.domain.comment.controller;

import com.example.todoapp.domain.comment.dto.CommentResponseDto;
import com.example.todoapp.domain.comment.dto.CommentSaveDto;
import com.example.todoapp.domain.comment.dto.CommentUpdateDto;
import com.example.todoapp.domain.comment.service.CommentService;
import com.example.todoapp.global.response.CustomResponse;
import com.example.todoapp.global.response.ResponseList;
import com.example.todoapp.global.response.StatusEnum;
import com.example.todoapp.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<ResponseList> getComments(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId) {
        List<CommentResponseDto> responseDtoList = commentService.getComments(userDetails, postId);
        return ResponseEntity.ok(new ResponseList<>(responseDtoList));
    }

    @PostMapping
    public ResponseEntity<CustomResponse> saveComment(@Valid @RequestBody CommentSaveDto request, @AuthenticationPrincipal UserDetailsImpl userDetails
                                                        , @PathVariable Long postId) {
        commentService.saveComment(request, userDetails, postId);
        return CustomResponse.toResponseEntity(StatusEnum.CREATE_COMMENT_SUCCESS);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CustomResponse> updatePost(@Valid @RequestBody CommentUpdateDto request, @AuthenticationPrincipal UserDetailsImpl userDetails
                                                        , @PathVariable Long postId, @PathVariable Long commentId) {
        commentService.updateComment(request, userDetails, postId, commentId);
        return CustomResponse.toResponseEntity(StatusEnum.UPDATE_COMMENT_SUCCESS);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CustomResponse> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(userDetails, postId, commentId);
        return CustomResponse.toResponseEntity(StatusEnum.DELETE_COMMENT_SUCCESS);
    }

}
