package com.example.todoapp.global.exception;

import com.example.todoapp.global.response.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<CustomResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return CustomResponse.toResponseEntity(e);
    }

    @ExceptionHandler
    public ResponseEntity<CustomResponse> handleCustomException(CustomException e) {
        return CustomResponse.toResponseEntity(e.getStatusEnum());
    }

}
