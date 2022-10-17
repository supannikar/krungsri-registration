package com.krungsri.service;

import com.krungsri.MemberType;
import com.krungsri.exception.InvalidateSalaryLevelException;
import com.krungsri.mapper.UserMapper;
import com.krungsri.model.RegistrationRequest;
import com.krungsri.model.RegistrationResponse;
import com.krungsri.repository.UserEntity;
import com.krungsri.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {
    private RegistrationService sut;
    private final Fixture fixture = new Fixture();

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;


    @BeforeEach
    void setUp() {
        this.sut = new RegistrationService(userRepository, userMapper);
    }

    @Test
    void onSuccessRegisterUser() {
        Mockito.when(userRepository.save(any())).thenReturn(fixture.userEntity);
        Mockito.when(userMapper.toUserResponse(any())).thenReturn(fixture.registrationResponse);

        var result = sut.registerUser(fixture.registrationRequest);
        Assertions.assertEquals(MemberType.GOLD.value, result.getMemberType());
    }

    @Test
    void onFailedRegisterUser() {
        final RegistrationRequest request = fixture.registrationRequest;
        request.setSalary(14999);
        assertThrows(InvalidateSalaryLevelException.class, () -> sut.registerUser(request));
    }

    static class Fixture {
        final RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username("mock-user")
                .password("pass1234")
                .phone("0851234646")
                .address("Bangkok Thailand")
                .salary(30000)
                .build();

        final UserEntity userEntity = UserEntity.builder()
                .username("mock-user")
                .password("pass1234")
                .phone("0851234646")
                .address("Bangkok Thailand")
                .memberType(MemberType.GOLD.value)
                .salary(30000)
                .build();

        final RegistrationResponse registrationResponse = RegistrationResponse.builder()
                .username("mock-user")
                .phone("0851234646")
                .address("Bangkok Thailand")
                .memberType(MemberType.GOLD.value)
                .salary(30000)
                .build();

    }
}