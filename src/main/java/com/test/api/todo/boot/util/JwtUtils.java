package com.test.api.todo.boot.util;

import com.test.api.todo.auth.domain.model.AuthToken;
import com.test.api.todo.auth.domain.repository.AuthTokenRepository;
import com.test.api.todo.boot.exception.RestException;
import com.test.api.todo.user.service.impl.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final AuthTokenRepository authTokenRepository;

    public Key getKey() {
        byte[] keys = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keys);
    }

    public String generateAccessToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 4)))
                .setIssuer("AndMe")
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .claim("name", userDetails.getNickName())
                .compact();
    }

    /**
     * Refresh 토큰의 유효기간은 2주일으로 설정
     * @param authentication
     * @return
     */
    public String generateRefreshToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setIssuer("AndMe")
                .signWith(getKey(), SignatureAlgorithm.HS512)
//                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 15)))
                .setExpiration(new Date(System.currentTimeMillis() * (1000L * 60 * 60 * 24 * 7)))
                .compact();
    }

    /**
     * Token 으로부터 username 을 추출한다.
     * 만일, 만료된 Token 일 경우에도 예외를 발생시키지 않고 username 을 반환한다.
     * @param token
     * @return
     */
    public String getUsernameFromAccessToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            log.warn("만료된 Access Token 입니다.");
            return e.getClaims().getSubject();
        }
    }

    public String getNameFromAccessToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return (String) claims.get("name");
        }  catch (ExpiredJwtException e) {
            log.warn("만료된 Access Token 입니다.");
            return e.getClaims().getSubject();
        }
    }

    public String getAccessTokenFromBearer(String rawAccessToken) {
        return rawAccessToken.replace("Bearer ", "");
    }

    public Boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다. msg=" + e.getMessage());
            throw new ExpiredJwtException(null, null, "만료된 토큰입니다. msg=" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰 형식입니다. msg=" + e.getMessage());
            throw new UnsupportedJwtException("지원하지 않는 토큰 형식입니다. msg=" + e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("유효하지 않는 토큰입니다. msg=" + e.getMessage());
            throw new MalformedJwtException("유효하지 않는 토큰입니다. Refresh Token 을 활용한 재갱신이 필요합니다. msg=" + e.getMessage());
        } catch (SignatureException e) {
            log.error("유효하지 않은 Signature 입니다. msg=" + e.getMessage());
            throw new SignatureException("유효하지 않은 Signature 입니다. msg=" + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("유효하지 않은 인자입니다. msg=" + e.getMessage());
            throw new IllegalArgumentException("유효하지 않은 인자입니다. msg=" + e.getMessage());
        }
    }

    public Boolean validateRefreshToken(String fakeRefreshToken) {
        // rT 를 전달받아 해당하는 Refresh Token 을 가져온다.
        AuthToken authTokenEntity = authTokenRepository.findByRefreshToken(fakeRefreshToken)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "해당하는 Refresh Token 을 찾을 수 없습니다."));

        // Refresh Token 을 검증한다.
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(authTokenEntity.getRefreshToken());
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("만료된 Refresh Token 입니다.");
            return false;
        }
    }

}
