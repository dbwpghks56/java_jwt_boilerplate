package com.test.api.todo.auth.domain.model;

import com.test.api.todo.boot.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_auth_token")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class AuthToken extends BaseEntity {

    @Id
    private String seq;

    private Long userSeq;
    @Column(columnDefinition = "varchar(1000)")
    private String accessToken;
    @Column(columnDefinition = "varchar(1000)")
    private String refreshToken;

    public void updateAccessToken(String newAccessToken) {
        this.accessToken = newAccessToken;
    }

}
