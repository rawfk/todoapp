package com.example.todoapp.domain.post.service;

import com.example.todoapp.domain.post.dto.PostResponseDto;
import com.example.todoapp.domain.post.dto.PostSaveDto;
import com.example.todoapp.domain.post.dto.PostUpdateDto;
import com.example.todoapp.domain.post.entity.Post;
import com.example.todoapp.domain.post.repository.PostRepository;
import com.example.todoapp.domain.user.entity.User;
import com.example.todoapp.domain.user.repository.UserRepository;
import com.example.todoapp.global.exception.CustomException;
import com.example.todoapp.global.response.StatusEnum;
import com.example.todoapp.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<PostResponseDto> getPosts(UserDetailsImpl userDetails) {
        validateUser(userDetails);
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResponseDto::new)
                .toList();
    }

    public PostResponseDto getPost(UserDetailsImpl userDetails, Long postId) {
        userDetails.getUser();
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(StatusEnum.POST_NOT_FOUND));
        return new PostResponseDto(findPost);
    }

    public void savePost(PostSaveDto request, UserDetailsImpl userDetails) {
        Post post = Post.builder()
                .user(userDetails.getUser())
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        postRepository.save(post);
    }

    @Transactional
    public void updatePost(PostUpdateDto request, UserDetailsImpl userDetails, Long postId) {
        Post post = validatePost(userDetails, postId);
        if(request.getTitle() == null) request.setTitle(post.getTitle());
        if(request.getContent() == null) request.setContent(post.getContent());
        post.updatePost(request);
    }

    @Transactional
    public void deletePost(UserDetailsImpl userDetails, Long postId) {
        Post post = validatePost(userDetails, postId);
        postRepository.delete(post);
    }

    private Post validatePost(UserDetailsImpl userDetails, Long postId) {
        User findUser = validateUser(userDetails);
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(StatusEnum.POST_NOT_FOUND));
        if (!findUser.getNickname().equals(findPost.getUser().getNickname())) {
            throw new CustomException(StatusEnum.USER_NOT_MATCHED);
        }
        return findPost;
    }
    private User validateUser(UserDetailsImpl userDetails) {
        return userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new CustomException(StatusEnum.USER_NOT_FOUND));
    }
}
