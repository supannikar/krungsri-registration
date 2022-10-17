package com.krungsri.controller;

import com.krungsri.exception.InvalidCredentialException;
import com.krungsri.exception.UsernameNotFoundException;
import com.krungsri.model.JwtTokenResponse;
import com.krungsri.model.RegistrationRequest;
import com.krungsri.model.RegistrationResponse;
import com.krungsri.model.AuthenticationRequest;
import com.krungsri.service.JwtTokenService;
import com.krungsri.service.JwtUserDetailService;
import com.krungsri.service.RegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "Authentication")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final JwtTokenService jwtTokenService;
    private final JwtUserDetailService jwtUserDetailService;

    @Autowired
    public AuthController(final RegistrationService registrationService,
                          final JwtTokenService jwtTokenService,
                          final JwtUserDetailService jwtUserDetailService) {
        this.registrationService = registrationService;
        this.jwtTokenService = jwtTokenService;
        this.jwtUserDetailService = jwtUserDetailService;
    }

    @ApiOperation("Get JWT token.")
    @PostMapping(path = "/token",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtTokenResponse> token(
            @ApiParam(name="Jwt token", value = "Get JWT token", required = true)
            @RequestBody AuthenticationRequest authenticationRequest) {

        var userDetail = jwtUserDetailService.loadUserByUsername(authenticationRequest.getUsername());

        String jwtToken = jwtTokenService.createToken(userDetail);
        return ResponseEntity.status(HttpStatus.OK).body(JwtTokenResponse.builder()
                .username(authenticationRequest.getUsername())
                .accessToken(jwtToken)
                .build());
    }

    @ApiOperation("Register a new user.")
    @PostMapping(path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationResponse> register(
            @ApiParam(name="registerUser", value = "Register user", required = true)
            @RequestBody RegistrationRequest registrationRequest) {

        RegistrationResponse registerUser = registrationService.registerUser(registrationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
    }
}
