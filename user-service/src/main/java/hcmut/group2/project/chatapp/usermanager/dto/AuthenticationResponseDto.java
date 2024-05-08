package hcmut.group2.project.chatapp.usermanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponseDto {
    private String token;
    private String message;
    private String user_id;
}