package org.example.izzy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.example.izzy.constant.ApiConstant.*;

@RestController
@RequestMapping(API + V1 + AUTH)
public class AuthController {

    @PostMapping(LOGIN)
    public ResponseEntity<?> login() {
return null;
    }
}

