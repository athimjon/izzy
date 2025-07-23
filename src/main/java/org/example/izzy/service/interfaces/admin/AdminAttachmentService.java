package org.example.izzy.service.interfaces.admin;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AdminAttachmentService {
    UUID saveAttachment(MultipartFile file);

    void updateAttachment(UUID attachmentId, MultipartFile file);

//    void getFile(Long attachmentId, HttpServletResponse response);
}
