package org.example.izzy.model.dto.response.general;

public record AuthResponse(
        String message,
        UserRes user
) {
}
