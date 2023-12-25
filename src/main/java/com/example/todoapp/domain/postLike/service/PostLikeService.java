package com.example.todoapp.domain.postLike.service;

import com.example.todoapp.domain.post.entity.Post;
import com.example.todoapp.domain.post.repository.PostRepository;
import com.example.todoapp.domain.postLike.entity.PostLike;
import com.example.todoapp.domain.postLike.entity.PostLikePK;
import com.example.todoapp.domain.postLike.repository.PostLikeRepository;
import com.example.todoapp.domain.user.entity.User;
import com.example.todoapp.domain.user.repository.UserRepository;
import com.example.todoapp.global.exception.CustomException;
import com.example.todoapp.global.response.StatusEnum;
import com.example.todoapp.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public boolean like(UserDetailsImpl userDetails, Long postId) {
        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new CustomException(StatusEnum.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(StatusEnum.POST_NOT_FOUND));

        PostLikePK id = new PostLikePK(user, post);
        Optional<PostLike> findPostLike = postLikeRepository.findById(id);
        if (findPostLike.isPresent()) {
            postLikeRepository.delete(findPostLike.get());
            return false;
        } else {
            postLikeRepository.save(new PostLike(id));
            return true;
        }
    }
}