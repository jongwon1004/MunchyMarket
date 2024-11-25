package com.munchymarket.MunchyMarket.dto.sample;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class CustomMultipartFile implements MultipartFile {

    private final File file;

    public CustomMultipartFile(String filePath) {
        this.file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다: " + filePath);
        }
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getOriginalFilename() {
        return file.getName();
    }

    @Override
    public String getContentType() {
        // 파일 확장자 기반으로 MIME 타입을 추론할 수도 있음
        return "image/jpeg";
    }

    @Override
    public boolean isEmpty() {
        return file.length() == 0;
    }

    @Override
    public long getSize() {
        return file.length();
    }

    @Override
    public byte[] getBytes() throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        return inputStream.readAllBytes();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (InputStream inputStream = new FileInputStream(this.file);
             OutputStream outputStream = new FileOutputStream(dest)) {
            inputStream.transferTo(outputStream);
        }
    }
}
