package hcmut.group2.project.chatapp.usermanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserLoginDto {
    @NotNull(message = "Password cannot be null.")
    @NotEmpty(message = "Password cannot be empty.")
    @Size(min = 8, max = 63, message = "Username must be between 8 and 63 characters long")
    private String password;

    @NotNull(message = "Phone number cannot be null.")
    @NotEmpty(message = "Phone number cannot be empty.")
    @Pattern(regexp = "^[+]?[0-9]{10,11}$", message = "Invalid phone number format")
    @Size(min = 10, max = 12, message = "Invalid phone number length")
    private String phoneNumber;
}
