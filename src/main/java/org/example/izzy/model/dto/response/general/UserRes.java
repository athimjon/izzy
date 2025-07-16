package org.example.izzy.model.dto.response.general;

import java.util.List;

public record UserRes(
        Long id,
        String fullName,
        String phoneNumber,
        List<String> roles
) {
}
