package org.example.izzy.model.dto.request.admin;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record AdminColourVariantReq(
        @NotBlank(message = "Colour Name is required!")
        String colourName,

        @NotNull(message = "Product ID is missing!")
        UUID productId,

        @NotNull(message = "IsActive must not be null")
        Boolean isActive,

        @NotEmpty(message = "At least one Image ID must be provided")
        List<@NotNull(message = "Image ID must not be null") UUID> imageIds

) {
}
