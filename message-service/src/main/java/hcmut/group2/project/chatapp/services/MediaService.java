package hcmut.group2.project.chatapp.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import hcmut.group2.project.chatapp.entities.Media;
import hcmut.group2.project.chatapp.repositories.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.result.Output;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;

// Service
@Service
@RequiredArgsConstructor
@Slf4j
public class MediaService {
    private final MediaRepository mediaRepository;
    private final FileStorageService fileStorageService;

    // Include reference to your cloud storage service

    public Media saveFile(MultipartFile file) {
        try {
            String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
            String filePath = fileStorageService.uploadFile(uniqueFileName, file); // Implement  cloud storage upload

            Media media = new Media();
            media.setFilename(uniqueFileName);
            media.setContentType(file.getContentType());
            media.setFilePath(filePath);

            return mediaRepository.save(media);
        } catch (Exception e) {
            log.error("Failed to upload media", e);
            // Handle exceptions
            throw new RuntimeException("Error uploading file", e);
        }
    }

    public Optional<Media> getMedia(Long id) {
        return this.mediaRepository.findById(id);
    }

    public void streamFile(OutputStream os, String fileName ){
        try {
            this.fileStorageService.downloadFile(os, fileName);
        } catch (Exception e) {
            log.error("failed to download file {} from s3", fileName);
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID() + "-" + originalFileName;
    }
}
