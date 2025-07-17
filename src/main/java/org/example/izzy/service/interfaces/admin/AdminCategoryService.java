package org.example.izzy.service.interfaces.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.izzy.model.dto.request.admin.AdminCategoryReq;
import org.example.izzy.model.dto.response.admin.AdminCategoryRes;

import java.util.List;

public interface AdminCategoryService {
    List<AdminCategoryRes> getAllCategories();

    AdminCategoryRes saveCategory(@Valid AdminCategoryReq categoryReq);

    AdminCategoryRes updateCategory(@Positive Long categoryId, @Valid AdminCategoryReq categoryReq);

    String activateOrDeactivateCategory(Long categoryId);
}
