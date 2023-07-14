package com.test.api.todo.todo.domain.repository.custom;

import com.test.api.todo.todo.domain.model.Todo;
import com.test.api.todo.todo.enums.TodoEnums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoRepositoryCustom {
    Page<Todo> findTodoAllPageByUser(Long userSeq, Pageable pageable, TodoEnums todoEnums);
}
