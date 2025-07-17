package org.example.izzy.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.example.izzy.exception.ResourceNotFoundException;
import org.example.izzy.mapper.CategoryMapper;
import org.example.izzy.model.dto.request.admin.AdminCategoryReq;
import org.example.izzy.model.dto.response.admin.AdminCategoryRes;
import org.example.izzy.model.entity.Category;
import org.example.izzy.repo.CategoryRepository;
import org.example.izzy.service.interfaces.admin.AdminCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<AdminCategoryRes> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toAdminCategoryResList(categories);
    }

    @Override
    public AdminCategoryRes saveCategory(AdminCategoryReq categoryReq) {
        Category mapperCategoryEntity = categoryMapper.toCategoryEntity(categoryReq);
        Category savedCategory = categoryRepository.save(mapperCategoryEntity);
        return categoryMapper.toAdminCategoryRes(savedCategory);
    }

    @Override
    public AdminCategoryRes updateCategory(Long categoryId, AdminCategoryReq categoryReq) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category Not found with ID: " + categoryId));

        categoryMapper.updateCategoryFromAdminCategoryReq(categoryReq, category);

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toAdminCategoryRes(updatedCategory);

    }

    @Override
    public String activateOrDeactivateCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

        category.setIsActive(!category.getIsActive());
        return categoryRepository.save(category).getIsActive() ? "ACTIVATED✅" : "DEACTIVATED⛔";
    }
}
