package com.test.api.todo.todo.service;

import com.test.api.todo.todo.dto.request.TodoSaveRequestDto;
import com.test.api.todo.todo.dto.request.TodoUpdateRequestDto;
import com.test.api.todo.todo.dto.response.TodoDetailResponseDto;
import com.test.api.todo.todo.dto.response.TodoResponseDto;
import com.test.api.todo.todo.enums.TodoEnums;
import org.springframework.data.domain.Page;

public interface TodoService {
    Page<TodoResponseDto> findTodoAllPageByUser(TodoEnums state, Integer page, Integer size) throws Exception;
    void saveTodo(TodoSaveRequestDto todoSaveRequestDto) throws Exception;
    void updateTodo(TodoUpdateRequestDto todoUpdateRequestDto) throws Exception;
    void deleteTodo(Long id) throws Exception;
    TodoDetailResponseDto todoDetail(Long todoId) throws Exception;
}
