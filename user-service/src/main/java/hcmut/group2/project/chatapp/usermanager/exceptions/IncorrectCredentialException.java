package hcmut.group2.project.chatapp.usermanager.exceptions;

import org.springframework.security.core.AuthenticationException;

public class IncorrectCredentialException extends AuthenticationException {
    public IncorrectCredentialException(String message) {
        super(message);
    }
}
