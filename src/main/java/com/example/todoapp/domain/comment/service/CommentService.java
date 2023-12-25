package com.example.todoapp.domain.comment.service;

import com.example.todoapp.domain.comment.dto.CommentResponseDto;
import com.example.todoapp.domain.comment.dto.CommentSaveDto;
import com.example.todoapp.domain.comment.dto.CommentUpdateDto;
import com.example.todoapp.domain.comment.entity.Comment;
import com.example.todoapp.domain.comment.repository.CommentRepository;
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
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<CommentResponseDto> getComments(UserDetailsImpl userDetails, Long postId) {
        validateUser(userDetails);
        Post post = validatePost(postId);

        List<Comment> comments = commentRepository.findByPost(post);
        return comments.stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    public void saveComment(CommentSaveDto request, UserDetailsImpl userDetails, Long postId) {
        User user = validateUser(userDetails);
        Post post = validatePost(postId);
        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(request.getContent())
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(CommentUpdateDto request, UserDetailsImpl userDetails, Long postId, Long commentId) {
        Comment comment = validateComment(userDetails, postId, commentId);
        comment.updateComment(request);
    }

    @Transactional
    public void deleteComment(UserDetailsImpl userDetails, Long postId, Long commentId) {
        Comment comment = validateComment(userDetails, postId, commentId);
        commentRepository.delete(comment);
    }

    private Comment validateComment(UserDetailsImpl userDetails, Long postId, Long commentId) {
        User findUser = validateUser(userDetails);
        validatePost(postId);
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(StatusEnum.COMMENT_NOT_FOUND));

        if (!findUser.equals(findComment.getUser())) {
            throw new CustomException(StatusEnum.USER_NOT_MATCHED);
        }

        return findComment;
    }

    private Post validatePost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(StatusEnum.POST_NOT_FOUND));
    }
    private User validateUser(UserDetailsImpl userDetails) {
        return userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new CustomException(StatusEnum.USER_NOT_FOUND));
    }
}
