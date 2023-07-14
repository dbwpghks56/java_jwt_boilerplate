package com.test.api.todo.todo.domain.repository;

import com.test.api.todo.todo.domain.model.Todo;
import com.test.api.todo.todo.domain.repository.custom.TodoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {
}
