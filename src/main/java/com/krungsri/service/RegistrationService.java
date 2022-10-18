package com.krungsri.service;

import com.krungsri.MemberType;
import com.krungsri.exception.InvalidateSalaryLevelException;
import com.krungsri.mapper.UserMapper;
import com.krungsri.model.RegistrationRequest;
import com.krungsri.model.RegistrationResponse;
import com.krungsri.repository.UserEntity;
import com.krungsri.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RegistrationService {
    private static final Integer PLATINUM_SALARY_LEVEL = 50000;
    private static final Integer SILVER_SALARY_LEVEL = 30000;
    private static final Integer INVALIDATE_SALARY_LEVEL = 15000;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(final UserRepository userRepository,
                               final UserMapper userMapper,
                               final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public RegistrationResponse registerUser(final RegistrationRequest registrationRequest) {
        final MemberType memberType = checkMemberType(registrationRequest.getSalary());
        final UserEntity userEntity = UserEntity.builder()
                .username(registrationRequest.getUsername())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .phone(registrationRequest.getPhone())
                .address(registrationRequest.getAddress())
                .salary(registrationRequest.getSalary())
                .referenceCode(generateReferenceCode(registrationRequest.getPhone()))
                .memberType(memberType.value)
                .build();

        var saveNewUser = userRepository.save(userEntity);
        return userMapper.toUserResponse(saveNewUser);
    }

    private String generateReferenceCode(final String phone) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        final String registerDate = formatter.format(new Date());
        final String last4digit = phone.replaceAll("\\D", "")
                .replaceAll("(?=\\d{5})\\d", "");

        return Strings.concat(registerDate, last4digit);
    }

    private MemberType checkMemberType(final Integer salary) {
        if (salary < INVALIDATE_SALARY_LEVEL) {
            throw new InvalidateSalaryLevelException(salary.toString());
        }

        if (salary > PLATINUM_SALARY_LEVEL) {
            return MemberType.PLATINUM;
        }

        if (salary < SILVER_SALARY_LEVEL) {
            return MemberType.SILVER;
        }

        return MemberType.GOLD;

    }
}
