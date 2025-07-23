package org.example.izzy.model.dto.response.general;

import java.util.List;
import java.util.UUID;

public record UserRes(
        UUID id,
        String fullName,
        String phoneNumber,
        List<String> roles
) {
}
