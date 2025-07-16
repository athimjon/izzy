package org.example.izzy.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.izzy.exception.ResourceNotFoundException;
import org.example.izzy.model.entity.Role;
import org.example.izzy.model.entity.User;
import org.example.izzy.model.enums.Roles;
import org.example.izzy.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserRepository userRepository;
    @Value("${jwt.secret.key}")
    private String jwtSecretKey;
    @Value("${jwt.expiration}")
    private Integer expirationTimeInMills;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    public String generateToken(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new ResourceNotFoundException("User not found with phone number: " + phoneNumber));

        return Jwts.builder()
                .subject(phoneNumber)
                .claim("id", user.getId())
                .claim("isActive", user.getIsActive())
                .claim("roles", user.getRoles().stream().map(role ->
                        role.getRoleName().name()).collect(Collectors.joining(","))
                )
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTimeInMills))
                .signWith(getSecretKey())
                .compact();

    }


    public boolean validateToken(String token) {
        Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return true;

    }

    public User getUserObject(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Long id = claims.get("id", Long.class);
        Boolean isActive = claims.get("isActive", Boolean.class);
        String strRoles = (String) claims.get("roles");

        List<Role> roles = Arrays.stream(strRoles.split(","))
                .map(roleStr -> new Role(Roles.valueOf(roleStr)))
                .toList();

        return User.builder()
                .id(id)
                .isActive(isActive)
                .phoneNumber(claims.getSubject())
                .roles(roles)
                .build();
    }
}
