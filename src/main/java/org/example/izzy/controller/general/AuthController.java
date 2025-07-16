package org.example.izzy.controller.general;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.izzy.model.dto.request.general.LoginReq;
import org.example.izzy.model.dto.response.general.AuthResponse;
import org.example.izzy.service.interfaces.general.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.example.izzy.constant.ApiConstant.*;

@RestController
@RequestMapping(API + V1 + AUTH)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq loginReq, HttpServletResponse response) {
        AuthResponse authResponse = authService.logInUser(loginReq, response);
        return ResponseEntity.ok(authResponse);
    }




}

