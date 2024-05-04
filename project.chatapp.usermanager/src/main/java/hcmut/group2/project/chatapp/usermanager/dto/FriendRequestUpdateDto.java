package hcmut.group2.project.chatapp.usermanager.dto;

import hcmut.group2.project.chatapp.usermanager.enums.FriendshipStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestUpdateDto {
    @NotNull(message = "New Status cannot be null.")
    @NotEmpty(message = "New Status cannot be empty.")
    private FriendshipStatus status;
}