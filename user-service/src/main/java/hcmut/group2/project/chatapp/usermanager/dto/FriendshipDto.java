package hcmut.group2.project.chatapp.usermanager.dto;

import java.time.LocalDateTime;

import hcmut.group2.project.chatapp.usermanager.entities.ChatUser;
import hcmut.group2.project.chatapp.usermanager.entities.keys.FriendshipId;
import hcmut.group2.project.chatapp.usermanager.enums.FriendshipStatus;
import lombok.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipDto {
    private FriendshipId id;
    private ChatUser user;
    private ChatUser friend;
    private FriendshipStatus status;
    private LocalDateTime creationDatetime;
}