package com.example.todoapp.global.exception;

import com.example.todoapp.global.response.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private StatusEnum statusEnum;
}
