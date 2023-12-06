package com.springboot.simple.controller;

import com.springboot.simple.entity.Role;
import com.springboot.simple.entity.User;
import com.springboot.simple.dto.JWTAuthResponse;
import com.springboot.simple.exception.TaskAPIException;
import com.springboot.simple.dto.LoginRequest;
import com.springboot.simple.dto.RegisterRequest;
import com.springboot.simple.repository.RoleRepository;
import com.springboot.simple.repository.UserRepository;
import com.springboot.simple.config.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Api(value = "Auth controller exposes login and register REST APIs")
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;


    @ApiOperation(value = "REST API to login user")
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginRequest loginRequest){

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            return ResponseEntity.ok(new JWTAuthResponse(token, username));
        } catch (Exception e) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Please check email and password!");
        }
    }

    @ApiOperation(value = "REST API to Register user with role manager")
    @PostMapping("/register/manager")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterRequest registerRequest) {
        String role = "ROLE_MANAGER";
        return registerUserWithRole(registerRequest, role);
    }

    @ApiOperation(value = "REST API to Register user with role member")
    @PostMapping("/register/member")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        String role = "ROLE_MEMBER";
        return registerUserWithRole(registerRequest, role);
    }

    private ResponseEntity<String> registerUserWithRole(RegisterRequest registerRequest, String role) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(true);
        user.setName(registerRequest.getUsername());
        user.setPhoneNumber("");
        user.setAddress("");
        user.setVerificationCode("");

        Role roles = roleRepository.findByName(role).orElseThrow(() ->
                new TaskAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found."));

        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
