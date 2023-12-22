package com.example.todoapp.global.security;

import com.example.todoapp.domain.user.dto.UserLoginDto;
import com.example.todoapp.domain.user.entity.User;
import com.example.todoapp.global.jwt.JwtUtil;
import com.example.todoapp.global.redis.RedisRepository;
import com.example.todoapp.global.response.CustomResponse;
import com.example.todoapp.global.response.StatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final RedisRepository redisRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, ObjectMapper objectMapper, RedisRepository redisRepository) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.redisRepository = redisRepository;
        setFilterProcessesUrl("/api/auth/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginDto requestDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);

            return getAuthenticationManager()
                    .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                requestDto.getNickname(),
                                requestDto.getPassword(),
                                null));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        User user = userDetails.getUser();
        String username = userDetails.getUsername();
        UserRoleEnum role = user.getRole();

        String accessToken = jwtUtil.createAccessToken(username, role);
        String refreshToken = jwtUtil.createRefreshToken(username, role);

        response.addHeader(jwtUtil.ACCESS_TOKEN_HEADER, accessToken);
        response.addHeader(jwtUtil.REFRESH_TOKEN_HEADER, refreshToken);

        redisRepository.setRefreshToken(user.getNickname(), refreshToken.substring(7));

        CustomResponse result = CustomResponse.from(StatusEnum.LOGIN_SUCCESS);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        CustomResponse commonResponseDto = CustomResponse.from(StatusEnum.LOGIN_FAIL);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(commonResponseDto));
    }
}