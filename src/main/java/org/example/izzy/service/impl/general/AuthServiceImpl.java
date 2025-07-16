package org.example.izzy.service.impl.general;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.izzy.config.security.JwtService;
import org.example.izzy.exception.InvalidCredentialsException;
import org.example.izzy.exception.ResourceNotFoundException;
import org.example.izzy.model.dto.request.general.LoginReq;
import org.example.izzy.model.dto.response.general.AuthResponse;
import org.example.izzy.model.dto.response.general.UserRes;
import org.example.izzy.model.entity.User;
import org.example.izzy.repo.UserRepository;
import org.example.izzy.service.interfaces.general.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.expiration}")
    private Integer expirationTimeInMills;

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;





    @Override
    public AuthResponse logInUser(LoginReq loginReq, HttpServletResponse response) {

        User user = userRepository.findByPhoneNumber(loginReq.phoneNumber()).orElseThrow(() ->
                new ResourceNotFoundException("User not found with Phone number : " + loginReq.phoneNumber()));

        // Check if user is active
        if (!user.isEnabled()) {
            throw new DisabledException("User account is disabled");
        }

        try {
            // Perform authentication
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginReq.phoneNumber(), loginReq.password()
            );
            authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid phone number or password");
        }

        generateTokenAndSetToCookie(loginReq.phoneNumber(), response);

        return mapToAuthResponse(user);
    }


    private AuthResponse mapToAuthResponse(User user) {
        UserRes userRes = new UserRes(
                user.getId(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getRoles().stream().map(Object::toString).toList()
        );
        return new AuthResponse("We are happy to see you back😁, " + user.getFullName(), userRes);
    }

    private void generateTokenAndSetToCookie(String phoneNumber, HttpServletResponse response) {
        String token = jwtService.generateToken(phoneNumber);
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false) // Only if using HTTPS
                .path("/") // Available across the app
                .maxAge(expirationTimeInMills / 1000) //Time given in seconds
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

}
