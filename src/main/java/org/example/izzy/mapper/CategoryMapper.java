package org.example.izzy.mapper;

import org.example.izzy.mapper.helper.AttachmentMapperHelper;
import org.example.izzy.mapper.helper.CategoryMapperHelper;
import org.example.izzy.mapper.helper.GeneralMapperHelper;
import org.example.izzy.model.dto.request.admin.AdminCategoryReq;
import org.example.izzy.model.dto.response.admin.AdminCategoryRes;
import org.example.izzy.model.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapperHelper.class, GeneralMapperHelper.class, AttachmentMapperHelper.class})
public interface CategoryMapper {

    @Mapping(source = "attachment.fileUrl", target = "attachmentUrl")
    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    @Mapping(source = "children", target = "childrenIds", qualifiedByName = "mapChildrenToChildrenIds")
    @Mapping(source = "children", target = "childrenNames", qualifiedByName = "mapChildrenToChildrenNames")
    AdminCategoryRes toAdminCategoryRes(Category category);

    List<AdminCategoryRes> toAdminCategoryResList(List<Category> categories);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "mapParentIdToCategory")
    @Mapping(target = "attachment", source = "attachmentId", qualifiedByName = "mapAttachmentIdToAttachment")
    Category toCategoryEntity(AdminCategoryReq adminCategoryReq);



    @Mapping(target = "parent", source = "parentId", qualifiedByName = "mapParentIdToCategory")
    @Mapping(target = "attachment", source = "attachmentId", qualifiedByName = "mapAttachmentIdToAttachment")
    void updateCategoryFromAdminCategoryReq(AdminCategoryReq req, @MappingTarget Category category); ///We use @MappingTarget to update the existing Category entity in-place.


}
