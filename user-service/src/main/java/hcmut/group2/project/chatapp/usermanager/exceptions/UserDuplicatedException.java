package hcmut.group2.project.chatapp.usermanager.exceptions;

public class UserDuplicatedException extends RuntimeException {
    public UserDuplicatedException(String message) {
        super(message);
    }
}
