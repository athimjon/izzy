package org.example.izzy.service.interfaces.admin;

import org.springframework.web.multipart.MultipartFile;

public interface AdminAttachmentService {
    Long saveAttachment(MultipartFile file);

    void updateAttachment(Long attachmentId, MultipartFile file);

//    void getFile(Long attachmentId, HttpServletResponse response);
}
