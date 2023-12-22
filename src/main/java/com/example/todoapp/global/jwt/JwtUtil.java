package com.example.todoapp.global.jwt;

import com.example.todoapp.global.exception.CustomException;
import com.example.todoapp.global.response.StatusEnum;
import com.example.todoapp.global.security.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j(topic = "Jwt")
public class JwtUtil {

	public static final String AUTHORIZATION_KEY = "auth";
	public static final String BEARER_PREFIX = "Bearer ";
	public static final long ACCESS_TOKEN_TIME  = 1 * 10 * 60 * 1000L;
	public static final String ACCESS_TOKEN_HEADER = "Access-Token";
	public static final long REFRESH_TOKEN_TIME  = 7 * 24 * 60 * 60 * 1000L;
	public static final String REFRESH_TOKEN_HEADER = "Refresh-Token";

	@Value("${jwt.secret.key}")
	private String secretKey;

	private Key key;

	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
	}

	public String resolveToken(HttpServletRequest request, String header) {
		String bearerToken = request.getHeader(header);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}
	public String createAccessToken(String username, UserRoleEnum role) {
		return this.createToken(username, role, ACCESS_TOKEN_TIME);
	}

	public String createRefreshToken(String username, UserRoleEnum role) {
		return this.createToken(username, role, REFRESH_TOKEN_TIME);
	}

	private String createToken(String username, UserRoleEnum role, long expiration) {
		Date date = new Date();

		return BEARER_PREFIX +
				Jwts.builder()
						.setSubject(username)
						.setExpiration(new Date(date.getTime() + expiration))
						.claim(AUTHORIZATION_KEY, role)
						.setIssuedAt(date)
						.signWith(key, signatureAlgorithm)
						.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token, 만료된 JWT token 입니다.");
			return false;
		} catch (SecurityException | MalformedJwtException | SignatureException e) {
			log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
			throw new CustomException(StatusEnum.TOKEN_NOT_VALID);
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
			throw new CustomException(StatusEnum.TOKEN_NOT_VALID);
		} catch (IllegalArgumentException e) {
			log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
			throw new CustomException(StatusEnum.TOKEN_NOT_VALID);
		}
	}

	public Claims getUserInfoFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public UserRoleEnum getUserRole(Claims claims) {
		return UserRoleEnum.valueOf(claims.get(AUTHORIZATION_KEY, String.class));
	}
}
