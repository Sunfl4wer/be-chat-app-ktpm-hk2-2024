package hcmut.group2.project.chatapp.usermanager.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity<String> handleUserDuplicated(UserDuplicatedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(FriendshipNotFoundException.class)
    public ResponseEntity<String> handleFriendshipNotFound(FriendshipNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(FriendshipDuplicatedException.class)
    public ResponseEntity<String> handleFriendshipDuplicated(FriendshipDuplicatedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    
    @ExceptionHandler(FriendshipBlockedException.class)
    public ResponseEntity<String> handleFriendshipBlocked(FriendshipBlockedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(IncorrectCredentialException.class)
    public ResponseEntity<String> handleIncorrectCredentialException(IncorrectCredentialException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    // Generic handler for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        // Log the exception details for debugging purposes
        e.printStackTrace(); // You should replace this with proper logging
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
