package com.krungsri.service;

import com.krungsri.exception.UsernameNotFoundException;
import com.krungsri.mapper.UserMapper;
import com.krungsri.model.RegistrationResponse;
import com.krungsri.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(final UserRepository userRepository,
                       final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<RegistrationResponse> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    public RegistrationResponse findByUsername(final String username) {
        var user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return userMapper.toUserResponse(user.get());
    }
}
