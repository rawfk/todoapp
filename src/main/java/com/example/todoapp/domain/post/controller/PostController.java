package com.example.todoapp.domain.post.controller;

import com.example.todoapp.domain.post.dto.PostResponseDto;
import com.example.todoapp.domain.post.dto.PostSaveDto;
import com.example.todoapp.domain.post.dto.PostUpdateDto;
import com.example.todoapp.domain.post.service.PostService;
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
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<ResponseList> getPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<PostResponseDto> responseDtoList = postService.getPosts(userDetails);
        return ResponseEntity.ok(new ResponseList<>(responseDtoList));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.getPost(userDetails, postId);
        return ResponseEntity.ok(postResponseDto);
    }

    @PostMapping("/posts")
    public ResponseEntity<CustomResponse> savePost(@Valid @RequestBody PostSaveDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.savePost(request, userDetails);
        return CustomResponse.toResponseEntity(StatusEnum.CREATE_POST_SUCCESS);
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<CustomResponse> updatePost(@Valid @RequestBody PostUpdateDto request, @AuthenticationPrincipal UserDetailsImpl userDetails
                                                        , @PathVariable Long postId) {
        postService.updatePost(request, userDetails, postId);
        return CustomResponse.toResponseEntity(StatusEnum.UPDATE_POST_SUCCESS);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<CustomResponse> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId) {
        postService.deletePost(userDetails, postId);
        return CustomResponse.toResponseEntity(StatusEnum.DELETE_POST_SUCCESS);
    }
}
