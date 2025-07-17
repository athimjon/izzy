package org.example.izzy.model.dto.response.admin;

import java.time.LocalDateTime;
import java.util.Set;

public record AdminCategoryRes(
        Long id,
        String name,

        Long parentId,
        String parentName,

        String attachmentUrl,

        Set<Long> childrenIds,
        Set<String> childrenNames,

        Boolean isActive,

        String createdBy,
        String updatedBy,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
