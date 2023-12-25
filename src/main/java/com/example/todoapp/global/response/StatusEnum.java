package com.example.todoapp.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StatusEnum {

    SIGNUP_SUCCESS(HttpStatus.CREATED, "SIGNUP_SUCCESS", "회원가입에 성공하였습니다."),
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "NICKNAME_DUPLICATED", "중복된 닉네임이 존재합니다."),
    PASSWORD_NOT_SAFE(HttpStatus.BAD_REQUEST, "PASSWORD_NOT_PROPER", "닉네임과 동일한 비밀번호를 사용할 수 없습니다."),
    PASSWORD_CHECK_FAIL(HttpStatus.BAD_REQUEST, "PASSWORD_CHECK_FAIL", "비밀번호와 비밀번호(확인)이 일치하지 않습니다."),

    LOGIN_SUCCESS(HttpStatus.OK, "LOGIN_SUCCESS", "로그인에 성공하였습니다."),
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, "LOGIN_FAIL", "닉네임 또는 패스워드를 확인해주세요."),
    LOGOUT_SUCCESS(HttpStatus.OK, "LOGOUT_SUCCESS", "로그아웃에 성공하였습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "사용자가 존재하지 않습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "게시글이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND", "댓글이 존재하지 않습니다."),

    USER_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "USER_NOT_MATCHED", "작성자만 가능합니다."),
    COMMENT_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "COMMENT_NOT_MATCHED", "작성자만 가능합니다.."),

    CREATE_POST_SUCCESS(HttpStatus.OK, "CREATE_POST_SUCCESS", "게시글이 등록되었습니다."),
    UPDATE_POST_SUCCESS(HttpStatus.OK, "UPDATE_POST_SUCCESS", "게시글이 수정되었습니다."),
    DELETE_POST_SUCCESS(HttpStatus.OK, "DELETE_POST_SUCCESS", "게시글이 삭제되었습니다."),

    POST_LIKE_SUCCESS(HttpStatus.OK, "POST_LIKE_SUCCESS", "게시글을 좋아요 하셨습니다."),
    POST_LIKE_CANCEL(HttpStatus.OK, "POST_LIKE_CANCEL", "게시글 좋아요를 취소하셨습니다."),
    
    CREATE_COMMENT_SUCCESS(HttpStatus.OK, "CREATE_COMMENT_SUCCESS", "댓글이 등록되었습니다."),
    UPDATE_COMMENT_SUCCESS(HttpStatus.OK, "UPDATE_COMMENT_SUCCESS", "댓글이 수정되었습니다."),
    DELETE_COMMENT_SUCCESS(HttpStatus.OK, "DELETE_COMMENT_SUCCESS", "댓글이 삭제되었습니다."),
    
    COMMENT_LIKE_SUCCESS(HttpStatus.OK, "COMMENT_LIKE_SUCCESS", "댓글을 좋아요 하셨습니다."),
    COMMENT_LIKE_CANCEL(HttpStatus.OK, "COMMENT_LIKE_CANCEL", "댓글 좋아요를 취소하셨습니다."),

    TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "TOKEN_NOT_VALID", "토큰이 유효하지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "ACCESS_DENIED", "접근 권한이 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String description;
    private final String message;
}
