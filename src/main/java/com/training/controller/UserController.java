package com.training.controller;

import com.training.dto.AuthRequest;
import com.training.dto.AuthResponse;
import com.training.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> createAuthenticationToken(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(userService.authentication(authRequest));
    }

}
