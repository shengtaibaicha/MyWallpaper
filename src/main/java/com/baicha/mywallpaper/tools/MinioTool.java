package com.baicha.mywallpaper.tools;

import io.minio.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@Component
public class MinioTool {

    @Value(value = "${minio.url}")
    private String uri;

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    /**
     * 初始化存储桶
     */
    public void init() {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("初始化MinIO存储桶失败");
        }
    }

    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile file, String fileName, String contentType) throws IOException {
        try {
            InputStream inputStream = file.getInputStream();

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(contentType)
                    .build());

            // 生成永久URL（需确保存储桶公开可读）
            return uri + "/" + bucketName + "/" + fileName;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传文件失败");
        }
    }

    /**
     * 上传压缩文件
     */
    public String uploadFileSE(ByteArrayInputStream inputStream, String fileName, String contentType, Long size) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, size, -1)
                    .contentType(contentType)
                    .build());

            // 生成永久URL（需确保存储桶公开可读）
            return uri + "/" + bucketName + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传文件失败");
        }
    }

    /**
     * 下载文件
     */
    public void downloadFile(String fileName, HttpServletResponse response) {
        try (InputStream in = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build())) {

            // 设置响应头
            response.setContentType(getMimeType(fileName));
            response.setHeader("Content-Disposition", "attachment; filename=\"" +
                    URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20") + "\"");

            // 复制文件流到响应输出流
            IOUtils.copy(in, response.getOutputStream());
            response.flushBuffer();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.reset();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("application/json");
                response.getWriter().write("{\"code\":500,\"message\":\"下载失败：" + e.getMessage() + "\"}");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("下载文件失败", e);
        }
    }

    // 辅助方法：根据文件扩展名获取MIME类型
    private String getMimeType(String fileName) {
        String extension = FilenameUtils.getExtension(fileName).toLowerCase();
        switch (extension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "pdf":
                return "application/pdf";
            case "txt":
                return "text/plain";
            case "zip":
                return "application/zip";
            default:
                return "application/octet-stream";
        }
    }

    /**
     * 删除文件
     */
    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除文件失败");
        }
    }
}
