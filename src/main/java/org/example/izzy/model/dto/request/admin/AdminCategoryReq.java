package org.example.izzy.model.dto.request.admin;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AdminCategoryReq(

        @NotBlank(message = "Category name is required and cannot be blank.")
        String name,

        @Nullable
        UUID parentId,

        @NotNull(message = "Attachment ID is required and cannot be null.")
        UUID attachmentId

) {
}
