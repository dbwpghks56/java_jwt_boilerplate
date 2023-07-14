package com.test.api.todo.todo.dto.response;

import com.test.api.todo.todo.domain.model.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class TodoDetailResponseDto {
    @Schema(description = "상세조회한 todo seq")
    private Long id;
    @Schema(description = "상세조회한 todo 이름")
    private String name;
    @Schema(description = "상세조회한 todo 내용")
    private String description;
    @Schema(description = "상세조회한 todo 상태")
    private Boolean state;

    @Builder
    public TodoDetailResponseDto(Todo entity) {
        this.id = entity.getSeq();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.state = entity.getStatus() != 1;
    }
}
