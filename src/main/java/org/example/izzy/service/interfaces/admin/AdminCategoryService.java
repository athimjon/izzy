package org.example.izzy.service.interfaces.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.izzy.model.dto.request.admin.AdminCategoryReq;
import org.example.izzy.model.dto.response.admin.AdminCategoryRes;

import java.util.List;
import java.util.UUID;

public interface AdminCategoryService {
    List<AdminCategoryRes> getAllCategories();

    AdminCategoryRes saveCategory(@Valid AdminCategoryReq categoryReq);

    AdminCategoryRes updateCategory(UUID categoryId, @Valid AdminCategoryReq categoryReq);

    String activateOrDeactivateCategory(UUID categoryId);

    AdminCategoryRes getOneCategory( UUID categoryId);
}
