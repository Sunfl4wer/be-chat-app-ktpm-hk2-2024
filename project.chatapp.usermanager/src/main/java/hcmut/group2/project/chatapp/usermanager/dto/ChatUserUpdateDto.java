package hcmut.group2.project.chatapp.usermanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserUpdateDto {
    @NotNull(message = "Username cannot be null.")
    @NotEmpty(message = "Username cannot be empty.")
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username can only contain alphanumeric characters, dots, dashes, and underscores.")
    private String username;

    @NotNull(message = "Password cannot be null.")
    @NotEmpty(message = "Password cannot be empty.")
    @Size(min = 8, max = 63, message = "Username must be between 8 and 63 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", 
             message = "Password must be at least 8 characters long, contain at least one number, one uppercase letter, one lowercase letter, and one special character.")
    private String password;

    @Email(message = "Email should be in correct format.")
    @Size(max = 255)
    private String emailAddress;

    @Size(max = 255)
    private String avatarUrl;

    @Size(max = 255)
    private String firstName;

    @Size(max = 255)
    private String lastName;

    private LocalDate birthDate;
}
