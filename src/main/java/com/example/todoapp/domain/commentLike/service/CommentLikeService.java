package com.example.todoapp.domain.commentLike.service;

import com.example.todoapp.domain.comment.entity.Comment;
import com.example.todoapp.domain.comment.repository.CommentRepository;
import com.example.todoapp.domain.commentLike.entity.CommentLike;
import com.example.todoapp.domain.commentLike.entity.CommentLikePK;
import com.example.todoapp.domain.commentLike.repository.CommentLikeRepository;
import com.example.todoapp.domain.post.repository.PostRepository;
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
public class CommentLikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public boolean like(UserDetailsImpl userDetails, Long postId, Long commentId) {
        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new CustomException(StatusEnum.USER_NOT_FOUND));

        postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(StatusEnum.POST_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(StatusEnum.COMMENT_NOT_FOUND));

        CommentLikePK id = new CommentLikePK(user, comment);
        Optional<CommentLike> findCommentLike = commentLikeRepository.findById(id);
        if (findCommentLike.isPresent()) {
            commentLikeRepository.delete(findCommentLike.get());
            return false;
        } else {
            commentLikeRepository.save(new CommentLike(id));
            return true;
        }
    }
}
