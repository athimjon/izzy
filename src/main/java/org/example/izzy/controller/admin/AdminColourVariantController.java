package org.example.izzy.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.izzy.model.dto.request.admin.AdminColourVariantReq;
import org.example.izzy.model.dto.response.admin.AdminColourVariantRes;
import org.example.izzy.service.interfaces.admin.AdminColourVariantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.example.izzy.constant.ApiConstant.*;

@RestController
@RequestMapping(API + V1 + ADMIN + COLOUR_VARIANT)
@RequiredArgsConstructor
public class AdminColourVariantController {

    private final AdminColourVariantService adminColourVariantService;

    @PostMapping
    public ResponseEntity<AdminColourVariantRes> createColourVariant(@Valid @RequestBody AdminColourVariantReq colourVariantReq) {
        AdminColourVariantRes adminColourVariantRes = adminColourVariantService.createColourVariant(colourVariantReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminColourVariantRes);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<AdminColourVariantRes>> getColourVariantByProductId(@PathVariable UUID productId) {
        List<AdminColourVariantRes> colourVariantRes = adminColourVariantService.getColourVariantsByProductId(productId);
        return ResponseEntity.ok(colourVariantRes);
    }

    @DeleteMapping("/{colourVariantId}")
    public ResponseEntity<Void> deleteColourVariantWithSizes(@PathVariable UUID colourVariantId) {
        adminColourVariantService.deleteColourVariantWithSizes(colourVariantId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{colourVariantId}")
    public ResponseEntity<AdminColourVariantRes> updateColourVariant(@PathVariable UUID colourVariantId,AdminColourVariantReq colourVariantReq) {
        AdminColourVariantRes colourVariantRes = adminColourVariantService.updateColourVariant(colourVariantId,colourVariantReq);
        return ResponseEntity.ok(colourVariantRes);
    }

}
