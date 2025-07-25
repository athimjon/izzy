package org.example.izzy.service.interfaces.admin;

import org.example.izzy.model.dto.request.admin.AdminProductReq;
import org.example.izzy.model.dto.response.admin.AdminProductRes;

import java.util.List;
import java.util.UUID;

public interface AdminProductService {

    AdminProductRes createProductWithoutItsVariants(AdminProductReq adminProductReq);

    AdminProductRes getOneProductWithoutItsVariants(UUID productId);

    List<AdminProductRes> getAllProductsWithoutVariants();



    AdminProductRes updateProductWithoutVariants(UUID productId, AdminProductReq adminProductReq);

    String disableOrEnableProduct(UUID productId);

}
