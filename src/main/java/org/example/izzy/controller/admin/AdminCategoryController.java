package org.example.izzy.controller.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.izzy.model.dto.request.admin.AdminCategoryReq;
import org.example.izzy.model.dto.response.admin.AdminCategoryRes;
import org.example.izzy.service.interfaces.admin.AdminCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.izzy.constant.ApiConstant.*;

@Validated
@RestController
@RequestMapping(API + V1 + ADMIN + CATEGORY)
@RequiredArgsConstructor
public class AdminCategoryController {


    private final AdminCategoryService adminCategoryService;

    @GetMapping
    public ResponseEntity<List<AdminCategoryRes>> getAllCategories() {
        List<AdminCategoryRes> categories = adminCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<AdminCategoryRes> getOneCategory(@PathVariable @Positive Long categoryId) {
       AdminCategoryRes category = adminCategoryService.getOneCategory(categoryId);
        return ResponseEntity.ok(category);
    }


    @PostMapping
    public ResponseEntity<?> saveCategory(@Valid @RequestBody AdminCategoryReq categoryReq) {
        AdminCategoryRes categoryRes = adminCategoryService.saveCategory(categoryReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryRes);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<AdminCategoryRes> updateCategory(
            @PathVariable @Positive Long categoryId,
            @Valid @RequestBody AdminCategoryReq categoryReq) {
        AdminCategoryRes adminCategoryRes = adminCategoryService.updateCategory(categoryId, categoryReq);
        return ResponseEntity.ok(adminCategoryRes);
    }


    @PatchMapping("/{categoryId}")
    public ResponseEntity<?> activateOrDeactivateCategory(@PathVariable @Positive Long categoryId) {
        String message = adminCategoryService.activateOrDeactivateCategory(categoryId);
        return ResponseEntity.ok("Category " + message);
    }
}
