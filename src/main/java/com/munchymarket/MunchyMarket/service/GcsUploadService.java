package com.munchymarket.MunchyMarket.service;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GcsUploadService {

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Value("${spring.cloud.gcp.credentials.location}") // 환경변수로 잡아놨음
    private String gcsCredentialsPath;

    private final ResourceLoader resourceLoader;

    /**     사진을 GCS 에 업로드하고 , serverFilename 을 return
     */
    public String uploadToGcs(MultipartFile image) throws IOException {

        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage 에 저장될 파일 이름
        String extGCS = image.getContentType(); // 파일의 형식  ex) image/png

        String[] extSplit = extGCS.split("/");  // {"image", "png"}
        String ext = (extSplit.length > 1) ? "." + extSplit[1] : ""; // "." 으로 .png

        String serverFilename = uuid + ext;

        // 서비스 계정 키 JSON 파일 경로를 지정
        Resource resource = resourceLoader.getResource("file:" + gcsCredentialsPath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream())
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));


        // Storage 객체를 credentials를 사용하여 초기화
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();

        // Cloud에 이미지 업로드
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, serverFilename)
                        .setContentType(extGCS)  // setContentType 은 GCS 객체의 메타데이터로 Content-Type 을 설정
                        .build(),
                image.getInputStream()
        );


        return serverFilename;
    }

    public Boolean deleteToGcs(String serverFilename) throws IOException {

        Resource resource = resourceLoader.getResource("file:" + gcsCredentialsPath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream())
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));


        // Storage 객체를 credentials를 사용하여 초기화
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();


        BlobId blobId = BlobId.of(bucketName, serverFilename);

        return storage.delete(blobId);
    }


}

