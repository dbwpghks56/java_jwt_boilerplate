package com.test.api.todo.todo.web;

import com.test.api.todo.todo.dto.request.TodoSaveRequestDto;
import com.test.api.todo.todo.dto.request.TodoUpdateRequestDto;
import com.test.api.todo.todo.dto.response.TodoDetailResponseDto;
import com.test.api.todo.todo.dto.response.TodoResponseDto;
import com.test.api.todo.todo.enums.TodoEnums;
import com.test.api.todo.todo.service.TodoService;
import com.test.api.todo.user.dto.response.VerifyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "[유저 & 관리자] Todo", description = "투두 API")
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    @GetMapping()
    @Tag(name = "[유저 & 관리자] Todo")
    @Operation(summary = "[유저 & 관리자] Todo 조회 API - JWT,페이지네이션", description = "로그인한 사용자의 Todo 를 페이지네이션으로 조회한다. <br> page는 1부터 조회할 수 있도록 했다.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<TodoResponseDto>> findTodoAllPageByUser(
            @RequestParam(required = false) TodoEnums state,
            @RequestParam Integer page,
            @RequestParam Integer size
            ) throws Exception {
        return ResponseEntity.ok(todoService.findTodoAllPageByUser(state, page, size));
    }

    @PostMapping()
    @Tag(name = "[유저 & 관리자] Todo")
    @Operation(summary = "[유저 & 관리자] Todo 기록 API - JWT", description = "로그인한 사용자가 입력한 Todo 를 기록한다.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveTodo(
            @RequestBody TodoSaveRequestDto todoSaveRequestDto
            ) throws Exception {
        todoService.saveTodo(todoSaveRequestDto);
        return null;
    }

    @DeleteMapping()
    @Tag(name = "[유저 & 관리자] Todo")
    @Operation(summary = "[유저 & 관리자] Todo 삭제 API - JWT", description = "로그인한 사용자가 요청한 Todo 를 삭제한다.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteTodo(
            @RequestParam Long id
    ) throws Exception {
        todoService.deleteTodo(id);
        return null;
    }

    @PutMapping()
    @Tag(name = "[유저 & 관리자] Todo")
    @Operation(summary = "[유저 & 관리자] Todo 수정 API - JWT", description = "로그인한 사용자가 요청한 Todo 를 수정한다.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateTodo(
            @RequestBody TodoUpdateRequestDto todoUpdateRequestDto
            ) throws Exception {
        todoService.updateTodo(todoUpdateRequestDto);
        return null;
    }

    @GetMapping("/{todoId}")
    @Tag(name = "[유저 & 관리자] Todo")
    @Operation(summary = "[유저 & 관리자] Todo 상세 조회 API - JWT", description = "로그인한 사용자가 요청한 Todo 를 상세 조회한다.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TodoDetailResponseDto> detailTodo(
            @PathVariable("todoId") Long todoId
    ) throws Exception {
        return ResponseEntity.ok(todoService.todoDetail(todoId));
    }
}









