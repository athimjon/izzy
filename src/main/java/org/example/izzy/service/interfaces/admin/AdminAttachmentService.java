package org.example.izzy.service.interfaces.admin;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AdminAttachmentService {
    Long saveAttachment(MultipartFile file);

    void getFile(Long attachmentId, HttpServletResponse response);
}
