package com.indigointelligence.stackademics.User;

import com.indigointelligence.stackademics.User.Records.LoginRequest;
import com.indigointelligence.stackademics.User.Records.LoginResponse;
import com.indigointelligence.stackademics.Utils.EntityResponse.EntityResponse;
import com.indigointelligence.stackademics.Utils.Security.JwtUtil.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth/")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("login")
    public EntityResponse<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
            );
            String jwt = jwtUtil.generateToken(authentication.getName());
            return EntityResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Login successful")
                    .entity(new LoginResponse(jwt))
                    .build();
        } catch (Exception e) {
            return EntityResponse.builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid credentials")
                    .build();
        }
    }

}

