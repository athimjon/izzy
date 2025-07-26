package org.example.izzy.service.interfaces.admin;

import org.example.izzy.model.dto.request.admin.AdminColourVariantWithNoSizesReq;
import org.example.izzy.model.dto.request.admin.AdminEntireColourVariantReq;
import org.example.izzy.model.dto.response.admin.AdminColourVariantRes;
import org.example.izzy.model.dto.response.admin.AdminEntireColourVariantRes;

import java.util.List;
import java.util.UUID;

public interface AdminColourVariantService {
    AdminEntireColourVariantRes createEntireColourVariant(AdminEntireColourVariantReq colourVariantReq);

    List<AdminEntireColourVariantRes> getEntireColourVariantsByProductId(UUID productId);

    AdminEntireColourVariantRes getOneEntireColourVariant(UUID colourVariantId);

    AdminColourVariantRes updateColourVariant(UUID colourVariantId, AdminColourVariantWithNoSizesReq colourVariantReq);

    String activateOrDeactivateColourVariant(UUID colourVariantId);


//TEMPORARILY  DISABLED DELETING ENDPOINT SERVICE
//    void deleteColourVariantWithSizes(UUID colourVariantId);
}
