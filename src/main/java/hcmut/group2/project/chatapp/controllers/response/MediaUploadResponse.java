package hcmut.group2.project.chatapp.controllers.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MediaUploadResponse {
    private Long id;
    private LocalDateTime uploadedAt;
}
