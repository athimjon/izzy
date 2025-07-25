package org.example.izzy.service.impl.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.izzy.exception.ResourceNotFoundException;
import org.example.izzy.model.entity.Attachment;
import org.example.izzy.repo.AttachmentRepository;
import org.example.izzy.service.interfaces.admin.AdminAttachmentService;
import org.example.izzy.service.interfaces.general.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAttachmentServiceImpl implements AdminAttachmentService {

    private final S3Service s3Service;
    private final AttachmentRepository attachmentRepository;

    @Override
    public UUID saveAttachment(MultipartFile file) {
        String fileUrl = s3Service.uploadImage(file);

        Attachment attachment = Attachment.builder()
                .fileUrl(fileUrl)
                .contentType(file.getContentType())
                .build();
        return attachmentRepository.save(attachment).getId();
    }

    @Override
    public void updateAttachment(UUID attachmentId, MultipartFile file) {

        Attachment existingAttachment = findAttachmentFromDB(attachmentId);

        // Yangi faylni S3 ga yuklash
        String fileUrl = s3Service.uploadImage(file);
        // Eski faylni S3 dan o'chirish
        //s3Service.deleteFile(existingAttachment.getFileUrl());


        // Attachment ni yangilash
        existingAttachment.setFileUrl(fileUrl);
        existingAttachment.setContentType(file.getContentType());
        try {
            attachmentRepository.save(existingAttachment);
        } catch (Exception e) {
            log.error("Failed to update attachment ID {}: {}", attachmentId, e.getMessage());
            throw new RuntimeException("Unable to update attachment in database", e);
        }
    }

    private Attachment findAttachmentFromDB(UUID attachmentId) {
        return attachmentRepository.findById(attachmentId).orElseThrow(() ->
                new ResourceNotFoundException("Updating Attachment not found with ID :  " + attachmentId));
    }


//    @SneakyThrows
//    @Override
//    public void getFile(Long attachmentId, HttpServletResponse response) {
//        Attachment attachment = attachmentRepository.findById(attachmentId)
//                .orElseThrow(() ->
//                        new RuntimeException("Attachment not found with ID: " + attachmentId));
//
//        byte[] image = s3Service.getImage(attachment.getFileUrl());
//        response.getOutputStream().write(image);
//    }
}
