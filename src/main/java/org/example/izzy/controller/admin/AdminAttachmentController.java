package org.example.izzy.controller.admin;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.izzy.service.interfaces.admin.AdminAttachmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/admin/attachment")
@RequiredArgsConstructor
public class AdminAttachmentController {


    private final AdminAttachmentService adminAttachmentService;

    @PostMapping
    public ResponseEntity<?> saveAttachment(@RequestParam("file") MultipartFile file) {
        Long attachmentId = adminAttachmentService.saveAttachment(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(attachmentId);
    }

    @GetMapping("/{attachmentId}")
    public ResponseEntity<Void> getAttachment(@PathVariable Long attachmentId, HttpServletResponse response) {
        adminAttachmentService.getFile(attachmentId, response);
        return ResponseEntity.ok().build();
    }


}
