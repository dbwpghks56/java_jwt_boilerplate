package com.test.api.todo.user.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.api.todo.boot.domain.BaseEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Slf4j
@Entity
@Table(name = "tb_user")
@Getter
@Setter
@ToString
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(columnDefinition = "varchar(100) NOT NULL")
    private String username;

    @JsonIgnore
    @Column(columnDefinition = "varchar(255) NOT NULL")
    private String password;

    @Column(columnDefinition = "varchar(100) NOT NULL")
    private String nickName;

    @Column(columnDefinition = "varchar(255)")
    private String phone;

    @Column(columnDefinition = "varchar(255)")
    private String crn;

    public void deleteUser(String crn) {
        this.crn = crn;
    }
}
