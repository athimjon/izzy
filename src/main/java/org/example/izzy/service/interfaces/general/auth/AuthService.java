package org.example.izzy.service.interfaces.general.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.izzy.model.dto.request.general.auth.LoginReq;
import org.example.izzy.model.dto.response.general.AuthResponse;

public interface AuthService {

    AuthResponse logInUser(@Valid LoginReq loginReq, HttpServletResponse response);

}
