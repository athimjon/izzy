package org.example.izzy.service.interfaces.general;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.izzy.model.dto.request.general.LoginReq;
import org.example.izzy.model.dto.response.general.AuthResponse;

public interface AuthService {

    AuthResponse logInUser(@Valid LoginReq loginReq, HttpServletResponse response);

}
