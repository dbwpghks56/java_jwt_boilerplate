package com.test.api.todo.todo.dto.request;

import com.test.api.todo.todo.domain.model.Todo;
import com.test.api.todo.todo.enums.TodoEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TodoUpdateRequestDto {
    @Schema(description = "업데이트할 todo seq")
    private Long id;
    @Schema(description = "업데이트할 todo 이름")
    private String name;
    @Schema(description = "업데이트할 todo 내용")
    private String description;
    @Schema(description = "업데이트할 todo 상태")
    private Boolean state;

}
