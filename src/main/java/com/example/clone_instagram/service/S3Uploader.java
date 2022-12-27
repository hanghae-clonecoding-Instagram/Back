package com.example.clone_instagram.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor    // final 멤버변수가 있으면 생성자 항목에 포함시킴
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 변환
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        if (multipartFile!=null) {
            File uploadFile = convert(multipartFile)                                                        // 파일 변환
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
            return upload(uploadFile, dirName);                                                         // id, uploadFile(파일이름), 디렉토리 이름 Return
        } else {
            return "https://wooo96bucket.s3.ap-northeast-2.amazonaws.com/static/Default_pfp.jpg";
        }

    }

    // 파일 업로드 메서드 호출 및 DB 이력 저장
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();                                         // 파일 이름 ( 디렉토리명 + / + 파일명 )
        String uploadImageUrl = putS3(uploadFile, fileName);                                            // 업로드 Image Url

        removeNewFile(uploadFile);                                                                      // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)
        return uploadImageUrl;                                                                          // 업로드된 파일의 S3 URL 주소 반환
    }

    // S3에 파일 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)	                            // PublicRead 권한으로 업로드
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 파일 삭제 로직
    private void removeNewFile(File targetFile) {                                                       // 삭제시에는 전체 경로를 다 반환해야함
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    // File Transfer (MultipartFile -> File)
    private Optional<File> convert(MultipartFile file) throws  IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

}