package com.test.api.todo.todo.domain.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.api.todo.todo.domain.model.QTodo;
import com.test.api.todo.todo.domain.model.Todo;
import com.test.api.todo.todo.dto.response.TodoResponseDto;
import com.test.api.todo.todo.enums.TodoEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QTodo todo = QTodo.todo;

    @Override
    public Page<Todo> findTodoAllPageByUser(Long userSeq, Pageable pageable, TodoEnums todoEnums) {
        List<Todo> results = new ArrayList<>();
        Long totalCount = 0L;
        if(todoEnums == TodoEnums.ALL) {
            results = queryFactory.selectFrom(todo)
                    .where(todo.user.seq.eq(userSeq))
                    .orderBy(todo.createdDTime.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
            totalCount = queryFactory.select(todo.count())
                    .where(todo.user.seq.eq(userSeq))
                    .from(todo)
                    .fetchOne();
        } else if (todoEnums == TodoEnums.COMPLETE) {
            results = queryFactory.selectFrom(todo)
                    .where(todo.user.seq.eq(userSeq).and(todo.status.ne(1)))
                    .orderBy(todo.createdDTime.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
            totalCount = queryFactory.select(todo.count())
                    .where(todo.user.seq.eq(userSeq).and(todo.status.ne(1)))
                    .from(todo)
                    .fetchOne();

        } else {
            results = queryFactory.selectFrom(todo)
                    .where(todo.user.seq.eq(userSeq).and(todo.status.eq(1)))
                    .orderBy(todo.createdDTime.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
            totalCount = queryFactory.select(todo.count())
                    .where(todo.user.seq.eq(userSeq).and(todo.status.eq(1)))
                    .from(todo)
                    .fetchOne();
        }

        return new PageImpl<>(results, pageable, totalCount);
    }
}
