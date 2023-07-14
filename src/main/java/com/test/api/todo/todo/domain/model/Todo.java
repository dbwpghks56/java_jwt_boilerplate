package com.test.api.todo.todo.domain.model;

import com.test.api.todo.boot.domain.BaseEntity;
import com.test.api.todo.todo.dto.request.TodoUpdateRequestDto;
import com.test.api.todo.user.domain.model.User;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Slf4j
@Entity
@Table(name = "tb_todo")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @Column(columnDefinition = "varchar(250)")
    private String name;
    @Column(columnDefinition = "varchar(250)")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void update(TodoUpdateRequestDto todoUpdateRequestDto) {
        if(todoUpdateRequestDto.getName() != null) {
            this.name = todoUpdateRequestDto.getName();
        }

        if(todoUpdateRequestDto.getDescription() != null) {
            this.description = todoUpdateRequestDto.getDescription();
        }

        if(todoUpdateRequestDto.getState() != null) {
            this.setStatus(!todoUpdateRequestDto.getState() ? 1 : 0);
        }
    }
}

























