package org.example.izzy.mapper;

import org.example.izzy.mapper.helper.AttachmentMapperHelper;
import org.example.izzy.mapper.helper.GeneralMapperHelper;
import org.example.izzy.model.dto.request.admin.AdminColourVariantReq;
import org.example.izzy.model.dto.response.admin.AdminColourVariantRes;
import org.example.izzy.model.entity.ColourVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GeneralMapperHelper.class, AttachmentMapperHelper.class})
public interface ColourVariantMapper {

    @Mapping(source = "productId", target = "product", qualifiedByName = "mapProductIdToProduct")
    @Mapping(source = "imageIds", target = "images", qualifiedByName = "mapAttachmentIdsToAttachments")
    ColourVariant toEntity(AdminColourVariantReq colourVariantReq);

    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "images", target = "imageUrls", qualifiedByName = "mapAttachmentsToUrls")
    @Mapping(source = "images", target = "imageIds", qualifiedByName = "mapAttachmentsToIds")
    AdminColourVariantRes toAdminColourVariantRes(ColourVariant savedEntity);

    List<AdminColourVariantRes> toAdminColourVariantResList(List<ColourVariant> colourVariantResList);

    @Mapping(source = "imageIds", target = "images", qualifiedByName = "mapAttachmentIdsToAttachments")
    @Mapping(source = "productId", target = "product", qualifiedByName = "mapProductIdToProduct")
    void updateColourVariantFromColourVariantReq(AdminColourVariantReq colourVariantReq, @MappingTarget ColourVariant colourVariant);
}
