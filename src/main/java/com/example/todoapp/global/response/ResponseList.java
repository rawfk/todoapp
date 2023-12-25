package com.example.todoapp.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseList<T> {
    private T data;
}
