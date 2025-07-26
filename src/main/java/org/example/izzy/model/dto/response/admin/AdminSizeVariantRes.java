package org.example.izzy.model.dto.response.admin;

import org.example.izzy.model.enums.Size;

import java.util.UUID;

public record AdminSizeVariantRes(
        UUID id,
        Size size,
        Integer quantity,
        UUID colourVariantId
) {
}
