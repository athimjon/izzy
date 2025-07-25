package org.example.izzy.service.interfaces.admin;

import org.example.izzy.model.dto.request.admin.AdminColourVariantReq;
import org.example.izzy.model.dto.response.admin.AdminColourVariantRes;

import java.util.List;
import java.util.UUID;

public interface AdminColourVariantService {
    AdminColourVariantRes createColourVariant(AdminColourVariantReq colourVariantReq);

    List<AdminColourVariantRes> getColourVariantsByProductId(UUID productId);

    void deleteColourVariantWithSizes(UUID colourVariantId);

    AdminColourVariantRes updateColourVariant(UUID colourVariantId, AdminColourVariantReq colourVariantReq);
}
