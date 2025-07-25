package org.example.izzy.service.interfaces.admin;

import org.example.izzy.model.dto.request.admin.AdminEntireColourVariantReq;
import org.example.izzy.model.dto.response.admin.AdminColourVariantRes;
import org.example.izzy.model.dto.response.admin.AdminEntireColourVariantRes;

import java.util.List;
import java.util.UUID;

public interface AdminColourVariantService {
    AdminEntireColourVariantRes createEntireColourVariant(AdminEntireColourVariantReq colourVariantReq);



    List<AdminColourVariantRes> getColourVariantsByProductId(UUID productId);

    void deleteColourVariantWithSizes(UUID colourVariantId);

    AdminColourVariantRes updateColourVariant(UUID colourVariantId, AdminEntireColourVariantReq colourVariantReq);
}
