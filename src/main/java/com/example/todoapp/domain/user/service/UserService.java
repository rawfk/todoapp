package com.example.todoapp.domain.user.service;

import com.example.todoapp.domain.user.dto.UserSignupDto;
import com.example.todoapp.domain.user.entity.User;
import com.example.todoapp.domain.user.repository.UserRepository;
import com.example.todoapp.global.exception.CustomException;
import com.example.todoapp.global.jwt.JwtUtil;
import com.example.todoapp.global.redis.RedisRepository;
import com.example.todoapp.global.response.StatusEnum;
import com.example.todoapp.global.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RedisRepository redisRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(UserSignupDto request) {

        checkSignupDto(request);

        String nickname = request.getNickname();

        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new CustomException(StatusEnum.NICKNAME_DUPLICATED);
        }

        String password = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .nickname(nickname)
                .password(password)
                .build();
        userRepository.save(user);
    }

    public void logout(String accessToken, String refreshToken, UserDetailsImpl userDetails) {
        //1. 유저 검증
        User user = userDetails.getUser();
        userRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(StatusEnum.USER_NOT_FOUND));
        String nickname = user.getNickname();

        //2. AccessToken black list에 등록
        Claims info = jwtUtil.getUserInfoFromToken(accessToken.substring(7));
        long issuedAt = info.getExpiration().getTime();
        long currentTime = new Date().getTime();

        long diff = issuedAt - currentTime;
        if (diff > 0) {
            long extra = 2000;
            redisRepository.setBlackList(accessToken.substring(7), nickname, diff + extra);
        }

        //3. RefreshToken 삭제
        if (redisRepository.hasRefreshToken(info.getSubject())) {
            redisRepository.deleteRefreshToken(nickname);
        }

    }

    private void checkSignupDto(UserSignupDto request) {

        String nickname = request.getNickname();
        String password = request.getPassword();
        String passwordChk = request.getPasswordChk();

        if (nickname.equals(password)) throw new CustomException(StatusEnum.PASSWORD_NOT_SAFE);
        if (!password.equals(passwordChk)) throw new CustomException(StatusEnum.PASSWORD_CHECK_FAIL);
    }
}
