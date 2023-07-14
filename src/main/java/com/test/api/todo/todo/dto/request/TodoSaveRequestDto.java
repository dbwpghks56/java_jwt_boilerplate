package com.test.api.todo.todo.dto.request;

import com.test.api.todo.todo.domain.model.Todo;
import com.test.api.todo.user.domain.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TodoSaveRequestDto {
    @Schema(description = "todo 이름")
    private String name;
    @Schema(description = "todo 내용")
    private String description;

    public Todo toEntity(User user){
        return Todo.builder()
                .name(name)
                .description(description)
                .user(user)
                .build();
    }
}
