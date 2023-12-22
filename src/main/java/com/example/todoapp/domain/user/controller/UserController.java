package com.example.todoapp.domain.user.controller;

import com.example.todoapp.domain.user.dto.UserSignupDto;
import com.example.todoapp.domain.user.service.UserService;
import com.example.todoapp.global.response.CustomResponse;
import com.example.todoapp.global.response.StatusEnum;
import com.example.todoapp.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/users/signup")
    public ResponseEntity<CustomResponse> signup(@Valid @RequestBody UserSignupDto request) {
        userService.signup(request);
        return CustomResponse.toResponseEntity(StatusEnum.SIGNUP_SUCCESS);
    }

    @PostMapping("/users/logout")
    public ResponseEntity<CustomResponse> logout(@RequestHeader("Access-Token") String accessToken, @RequestHeader(value = "Refresh-Token", required = false) String refreshToken,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.logout(accessToken, refreshToken, userDetails);
        return CustomResponse.toResponseEntity(StatusEnum.LOGOUT_SUCCESS);
    }
}
