package org.example.izzy.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.izzy.model.dto.request.admin.AdminProductReq;
import org.example.izzy.model.dto.response.admin.AdminProductRes;
import org.example.izzy.service.interfaces.admin.AdminProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.example.izzy.constant.ApiConstant.*;

@Validated
@RestController
@RequestMapping(API + V1 + ADMIN + PRODUCT)
@RequiredArgsConstructor
public class AdminProductController {
    private final AdminProductService adminProductService;


    @GetMapping
    public ResponseEntity<List<AdminProductRes>> getAllProducts() {
        List<AdminProductRes> productRes = adminProductService.getAllProducts();
        return ResponseEntity.ok(productRes);
    }


    @PostMapping
    public ResponseEntity<AdminProductRes> createProduct(@Valid @RequestBody AdminProductReq adminProductReq) {
        AdminProductRes adminProductRes = adminProductService.createProduct(adminProductReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminProductRes);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> editProduct(@PathVariable UUID productId, @Valid @RequestBody AdminProductReq adminProductReq) {
        AdminProductRes adminProductRes = adminProductService.editProduct(productId, adminProductReq);
        return ResponseEntity.ok(adminProductRes);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<?> disableOrEnableProduct(@PathVariable UUID productId) {
        String message = adminProductService.disableOrEnableProduct(productId);
        return ResponseEntity.ok(message);
    }
}
