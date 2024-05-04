package hcmut.group2.project.chatapp.controllers;

import hcmut.group2.project.chatapp.controllers.response.MediaUploadResponse;
import hcmut.group2.project.chatapp.entities.Media;
import hcmut.group2.project.chatapp.services.MediaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/media")
@Slf4j
public class MediaController implements JWTAuthController {
    @Autowired
    private MediaService mediaService;
    private static final Logger logger = LoggerFactory.getLogger(MediaController.class);

    @PostMapping("/upload")
    public ResponseEntity<MediaUploadResponse> uploadFile(@RequestParam("media_file") MultipartFile file) {
        Media media = mediaService.saveFile(file);
        return ResponseEntity.ok(MediaUploadResponse.builder()
                        .id(media.getId())
                        .uploadedAt(media.getCreatedAt())
                .build());
    }

    @GetMapping(value = "/download/{id}", produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public ResponseEntity<StreamingResponseBody> downloadFile(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              @PathVariable Long id) {
        final Optional<Media> maybeMedia = mediaService.getMedia(id);
        if (maybeMedia.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        final Media media = maybeMedia.get();
        final StreamingResponseBody body = outputStream -> mediaService.streamFile(outputStream, media.getFilename());
        response.setContentType(media.getContentType());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
