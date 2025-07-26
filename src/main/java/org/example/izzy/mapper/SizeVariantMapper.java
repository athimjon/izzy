package org.example.izzy.mapper;

import org.example.izzy.model.dto.request.admin.AdminSizeVariantReq;
import org.example.izzy.model.dto.response.admin.AdminSizeVariantRes;
import org.example.izzy.model.entity.SizeVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SizeVariantMapper {


    SizeVariant toEntity(AdminSizeVariantReq sizeVariantReq);

    List<SizeVariant> toEntityList(List<AdminSizeVariantReq> sizeVariantReqs);


    @Mapping(source = "colourVariant.id", target = "colourVariantId")
    AdminSizeVariantRes toAdminSizeVariantRes(SizeVariant sizeVariant);

    List<AdminSizeVariantRes> toAdminSizeVariantResList(List<SizeVariant> sizeVariants);


    void updateSizeVariantFromSizeVariantReq(@MappingTarget SizeVariant sizeVariantFromDB, AdminSizeVariantReq sizeVariantReq);
}
