package org.example.izzy.model.dto.request.admin;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record AdminCategoryReq(

        @NotBlank(message = "Category name is required and cannot be blank.")
        String name,

        @Nullable
        @Positive(message = "Parent category ID must be a positive number greater than zero.")
        Long parentId,

        @NotNull(message = "Attachment ID is required and cannot be null.")
        @Positive(message = "Attachment ID must be a positive number greater than zero.")
        Long attachmentId

) {
}
