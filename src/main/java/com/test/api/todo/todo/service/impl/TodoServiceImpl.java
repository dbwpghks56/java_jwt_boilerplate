package com.test.api.todo.todo.service.impl;

import com.test.api.todo.boot.exception.RestException;
import com.test.api.todo.todo.domain.model.Todo;
import com.test.api.todo.todo.domain.repository.TodoRepository;
import com.test.api.todo.todo.dto.request.TodoSaveRequestDto;
import com.test.api.todo.todo.dto.request.TodoUpdateRequestDto;
import com.test.api.todo.todo.dto.response.TodoDetailResponseDto;
import com.test.api.todo.todo.dto.response.TodoResponseDto;
import com.test.api.todo.todo.enums.TodoEnums;
import com.test.api.todo.todo.service.TodoService;
import com.test.api.todo.user.domain.model.User;
import com.test.api.todo.user.domain.repository.UserRepository;
import com.test.api.todo.user.service.impl.UserDetailsImpl;
import io.sentry.Sentry;
import io.sentry.spring.tracing.SentrySpan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@RequiredArgsConstructor
@Service
@Slf4j
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    /**
     * 로그인한 유저 todo 조회 페이지네이션 사용
     * @param state
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TodoResponseDto> findTodoAllPageByUser(TodoEnums state, Integer page, Integer size) throws Exception {
        // 유저 검증
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userRepository.existsById(userDetails.getSeq())) throw new RestException(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다.");
        if (state == null) {
            state = TodoEnums.ALL;
        }
        // 페이지네이션을 위한 변수 생성
        Pageable pageable = PageRequest.of(page-1, size == null ? 10 : size);
        
        // 페이지네이션 활용
        Page<Todo> todoEntityPage = todoRepository.findTodoAllPageByUser(userDetails.getSeq(), pageable, state);
        Page<TodoResponseDto> todoResponseDtoPage = todoEntityPage.map(TodoResponseDto::new);

        return todoResponseDtoPage;
    }

    /**
     * todo 기록
     * @param todoSaveRequestDto
     * @throws Exception
     */
    @Override
    @Transactional
    public void saveTodo(TodoSaveRequestDto todoSaveRequestDto) throws Exception {
        // 유저 검증
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findBySeq(userDetails.getSeq()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."));
        
        // 투두 entity 생성
        Todo todo = todoSaveRequestDto.toEntity(user);
        
        // 투두 저장
        todoRepository.save(todo);
    }

    /**
     * todo 업데이트
     * @param todoUpdateRequestDto
     * @throws Exception
     */
    @Override
    @Transactional
    public void updateTodo(TodoUpdateRequestDto todoUpdateRequestDto) throws Exception {
        // 유저 및 투두 검증
        Todo todo = verifyUserTodo(todoUpdateRequestDto.getId());

        // 검증 통과시 투두 업데이트
        todo.update(todoUpdateRequestDto);
    }

    /**
     * todo 삭제
     * @param id
     * @throws Exception
     */
    @Override
    @Transactional
    public void deleteTodo(Long id) throws Exception {
        // 유저 및 투두 검증
        Todo todo = verifyUserTodo(id);

        todoRepository.delete(todo);
    }

    /**
     * todo 상세 조회
     * @param todoId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    @SentrySpan(description = "todo 테스트")
    public TodoDetailResponseDto todoDetail(Long todoId) throws Exception {
        // 유저 및 투두 검증
        Todo todo = verifyUserTodo(todoId);

        // 검증 통과시 투두 조회 반환
        return TodoDetailResponseDto.builder().entity(todo).build();
    }

    private Todo verifyUserTodo(Long id) throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userRepository.existsById(userDetails.getSeq())) throw new RestException(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다.");
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "해당 id 의 todo 를 찾을 수 없습니다."));
        if (todo.getUser().getSeq() != userDetails.getSeq()) throw new RestException(HttpStatus.BAD_REQUEST, "해당 todo 의 주인이 아닙니다.");

        return todo;
    }
}
