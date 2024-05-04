package hcmut.group2.project.chatapp.usermanager.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDto {
    @NotNull(message = "Sender Phone Number cannot be null.")
    @NotEmpty(message = "Sender Phone Number cannot be empty.")
    private String senderPhoneNumber;
    
    @NotNull(message = "Receiver Phone Number cannot be null.")
    @NotEmpty(message = "Receiver Phone Number cannot be empty.")
    private String receiverPhoneNumber;
}