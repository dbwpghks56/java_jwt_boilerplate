package com.test.api.todo.auth.domain.repository;

import com.test.api.todo.auth.domain.model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {
    Optional<AuthToken> findByRefreshToken(String fakeRefreshToken);
    void deleteByRefreshToken(String fakeRefreshToken);
    void deleteByUserSeq(Long userSeq);
    Integer deleteByAccessToken(String accessToken);
    Integer deleteByUserSeqIn(List<Long> userSeqList);
    Boolean existsBySeq(String fakeRefreshToken);
    Boolean existsByUserSeq(Long userSeq);
    Boolean existsByAccessToken(String accessToken);
    Boolean existsByAccessTokenAndRefreshToken(String accessToken, String fakeRefreshToken);
}
