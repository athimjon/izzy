package org.example.izzy.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.izzy.service.interfaces.admin.AdminAttachmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.example.izzy.constant.ApiConstant.*;

@RestController
@RequestMapping(API+V1+ADMIN+ATTACHMENT)
@RequiredArgsConstructor
public class AdminAttachmentController {


    private final AdminAttachmentService adminAttachmentService;

    @PostMapping
    public ResponseEntity<?> saveAttachment(@RequestParam("file") MultipartFile file) {
        Long attachmentId = adminAttachmentService.saveAttachment(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(attachmentId);
    }


}
