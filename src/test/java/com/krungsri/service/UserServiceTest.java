package com.krungsri.service;

import com.krungsri.mapper.UserMapper;
import com.krungsri.mapper.UserMapperImpl;
import com.krungsri.model.RegistrationResponse;
import com.krungsri.repository.UserEntity;
import com.krungsri.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService sut;

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @BeforeEach
    void setUp() {
        this.sut = new UserService(userRepository, userMapper);
    }

    @Test
    void findAll() {
        Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());

        var result = sut.findAll();
        assertEquals(0, result.size());
    }

    @Test
    void findByUsername() {
        Mockito.when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(UserEntity.builder().username("username").build()));
        Mockito.when(userMapper.toUserResponse(any(UserEntity.class)))
                .thenReturn(RegistrationResponse.builder().username("username").build());

        var result = sut.findByUsername("username");
        assertEquals("username", result.getUsername());
    }
}