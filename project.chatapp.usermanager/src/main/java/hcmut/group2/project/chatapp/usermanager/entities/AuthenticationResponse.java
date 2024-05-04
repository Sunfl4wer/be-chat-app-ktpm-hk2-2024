package hcmut.group2.project.chatapp.usermanager.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String message;

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}