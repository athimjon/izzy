package org.example.izzy.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.example.izzy.model.dto.response.admin.AdminCategoryRes;
import org.example.izzy.model.entity.Attachment;
import org.example.izzy.repo.AttachmentRepository;
import org.example.izzy.service.interfaces.admin.AdminAttachmentService;
import org.example.izzy.service.interfaces.general.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAttachmentServiceImpl implements AdminAttachmentService {

    private final S3Service s3Service;
    private final AttachmentRepository attachmentRepository;

    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.s3.region}")
    private String bucketRegion;

    @Override
    public Long saveAttachment(MultipartFile file) {

        String key = s3Service.uploadImage(file);

        String fileUrl = "https://"+bucketName + ".s3." + bucketRegion + ".amazonaws.com/" + key;

        Attachment attachment = Attachment.builder()
                .fileUrl(fileUrl)
                .contentType(file.getContentType())
                .build();
        return attachmentRepository.save(attachment).getId();
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
