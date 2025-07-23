package org.example.izzy.service.interfaces.admin;

import org.example.izzy.model.dto.request.admin.AdminProductReq;
import org.example.izzy.model.dto.response.admin.AdminProductRes;

import java.util.List;
import java.util.UUID;

public interface AdminProductService {
    List<AdminProductRes> getAllProducts();

    AdminProductRes createProduct(AdminProductReq adminProductReq);

    AdminProductRes editProduct(UUID productId, AdminProductReq adminProductReq);

    String  disableOrEnableProduct(UUID productId);
}
