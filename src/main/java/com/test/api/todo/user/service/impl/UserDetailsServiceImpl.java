package com.test.api.todo.user.service.impl;

import com.test.api.todo.boot.exception.RestException;
import com.test.api.todo.boot.exception.UserNotFoundException;
import com.test.api.todo.user.domain.model.User;
import com.test.api.todo.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저를 찾을 수 없습니다. username = " + username));

        UserDetailsImpl userDetails = UserDetailsImpl.getUserDetails(userEntity);

        return userDetails;
    }
}













