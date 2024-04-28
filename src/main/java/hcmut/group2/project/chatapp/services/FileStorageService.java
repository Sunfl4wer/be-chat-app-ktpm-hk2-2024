package hcmut.group2.project.chatapp.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    private final AmazonS3 s3Client;

    public String uploadFile(String filename, MultipartFile multipartFile) throws IOException {
        String fileUrl = "";
        File file = null;
        try {
            file = convertMultiPartToFile(filename, multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + filename;
            uploadFileTos3bucket(filename, file);
        } catch (Exception e) {
            log.error("Failed to upload file to s3 storage", e);
            throw new RuntimeException(e);
        } finally {
            if (file != null) {
                boolean success = file.delete();
                if (!success) {
                    log.warn("failed to delete file {}", file);
                }
            }
        }
        return fileUrl;
    }

    public void downloadFile(OutputStream os, String fileName) throws IOException {
        if (bucketIsEmpty()) {
            throw new RuntimeException("Requested bucket does not exist or is empty");
        }
        S3Object object = s3Client.getObject(bucketName, fileName);
        try (S3ObjectInputStream s3is = object.getObjectContent()) {
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                os.write(read_buf, 0, read_len);
            }
        }
    }

    private boolean bucketIsEmpty() {
        ListObjectsV2Result result = s3Client.listObjectsV2(this.bucketName);
        if (result == null){
            return false;
        }
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        return objects.isEmpty();
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private File convertMultiPartToFile(String filename, MultipartFile file) throws IOException {
        File convFile = new File(filename);
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}