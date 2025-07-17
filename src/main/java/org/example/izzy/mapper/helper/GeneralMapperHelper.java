package org.example.izzy.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.example.izzy.exception.ResourceNotFoundException;
import org.example.izzy.model.entity.Attachment;
import org.example.izzy.model.entity.Category;
import org.example.izzy.repo.AttachmentRepository;
import org.example.izzy.repo.CategoryRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeneralMapperHelper {
    private final AttachmentRepository attachmentRepository;
    private final CategoryRepository categoryRepository;

    @Named("mapAttachmentIdToAttachment")
    public Attachment mapAttachmentIdToAttachment(Long attachmentId) {
        return attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment with ID " + attachmentId + " not found"));
    }
    @Named("mapParentIdToCategory")
    public Category mapParentIdToCategory(Long parentId) {
        if (parentId == null) {
            return null;
        }
        return categoryRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent Category with ID " + parentId + " not found!"));
    }


}
