package com.test.api.todo.todo.dto.response;

import com.test.api.todo.todo.domain.model.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class TodoResponseDto {
    @Schema(description = "조회한 todo seq")
    private Long id;
    @Schema(description = "조회한 todo 이름")
    private String name;
    @Schema(description = "조회한 todo 상태")
    private Boolean state;
    @Schema(description = "조회한 todo 만들어진 시간")
    private LocalDateTime createdAt;

    @Builder
    public TodoResponseDto(Todo entity) {
        this.id = entity.getSeq();
        this.name = entity.getName();
        this.state = entity.getStatus() != 1;
        this.createdAt = entity.getCreatedDTime();
    }
}
