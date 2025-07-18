package org.example.izzy.service.impl.general;

import lombok.RequiredArgsConstructor;
import org.example.izzy.service.interfaces.general.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.s3.region}")
    private String bucketRegion;

    private final S3Client s3Client;

    @Override
    public String uploadImage(MultipartFile file) {

        String key = generateKey(file);
        String fileUrl = generateFileUrl(key);


        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        try (InputStream inputStream = file.getInputStream()) {
            s3Client
                    .putObject(request, RequestBody.fromInputStream(inputStream, file.getSize()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return fileUrl;
    }


    private static String generateKey(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return System.currentTimeMillis() + "_" + fileName;
    }

    private String generateFileUrl(String key) {
        return "https://" + bucketName + ".s3." + bucketRegion + ".amazonaws.com/" + key;
    }


//    @SneakyThrows
//    @Override
//    public byte[] getImage(String url) {
//        GetObjectRequest request = GetObjectRequest.builder()
//                .bucket(bucketName)
//                .key(url)
//                .build();
//        try (ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(request)) {
//            return inputStream.readAllBytes();
//        }
//    }


}

