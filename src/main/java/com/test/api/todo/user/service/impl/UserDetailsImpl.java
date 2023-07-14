package com.test.api.todo.user.service.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.api.todo.user.domain.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@ToString
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    @ApiModelProperty(value = "유저 시퀀스")
    private Long seq;

    @ApiModelProperty(value = "유저 아이디")
    private String username;

    @ApiModelProperty(value = "유저 비밀번호")
    private String password;

    @ApiModelProperty(value = "유저 닉네임")
    private String nickName;

    @ApiModelProperty(value = "유저 휴대폰 번호")
    private String phone;

    @ApiModelProperty(value = "사업자 등록 번호")
    private String crn;

    @ApiModelProperty(value = "유저 생성일")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDtime;

    @ApiModelProperty(value = "유저 수정일")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDtime;

    @ApiModelProperty(value = "상태")
    private Integer status;

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Builder
    public UserDetailsImpl(
            Long seq,
            String username,
            String password,
            String nickName,
            String phone,
            String crn,
            LocalDateTime createdDtime,
            LocalDateTime modifiedDtime,
            Integer status
    ) {
        this.seq = seq;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.phone = phone;
        this.crn = crn;
        this.createdDtime = createdDtime;
        this.modifiedDtime = modifiedDtime;
        this.status = status;
    }

    public static UserDetailsImpl getUserDetails(User entity) {
        return UserDetailsImpl.builder()
                .seq(entity.getSeq())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .nickName(entity.getNickName())
                .phone(entity.getPhone())
                .crn(entity.getCrn())
                .createdDtime(entity.getCreatedDTime())
                .modifiedDtime(entity.getModifiedDTime())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
