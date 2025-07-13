package org.example.izzy.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    String uploadImage(MultipartFile file);

    byte[] getImage(String url);

}
