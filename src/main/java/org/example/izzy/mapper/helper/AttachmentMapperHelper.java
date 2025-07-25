package org.example.izzy.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.example.izzy.exception.ResourceNotFoundException;
import org.example.izzy.model.entity.Attachment;
import org.example.izzy.repo.AttachmentRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AttachmentMapperHelper {

    private final AttachmentRepository attachmentRepository;

    @Named("mapAttachmentIdToAttachment")
    public Attachment mapAttachmentIdToAttachment(UUID attachmentId) {
        return attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment with ID " + attachmentId + " not found"));
    }

    @Named("mapAttachmentsToUrls")
    public List<String> mapAttachmentsToUrls(List<Attachment> attachments) {
        if (attachments == null) return new ArrayList<>();
        return attachments.stream()
                .map(Attachment::getFileUrl)
                .toList();
    }

    @Named("mapAttachmentIdsToAttachments")
    public List<Attachment> mapAttachmentIdsToAttachments(List<UUID> attachmentIds) {
        if (attachmentIds == null || attachmentIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Attachment> attachments = attachmentRepository.findAllById(attachmentIds);

        if (attachments.size() != attachmentIds.size()) {
            throw new ResourceNotFoundException(attachmentIds.size() - attachments.size() + " - ATTACHMENTS were not found!");
        }

        return attachments;
    }

    @Named("mapAttachmentsToIds")
    public List<UUID> mapAttachmentsToIds(List<Attachment> attachments) {
        if (attachments == null || attachments.isEmpty()) {
            return new ArrayList<>();
        }
        return attachments.stream()
                .map(Attachment::getId)
                .toList();
    }

}
