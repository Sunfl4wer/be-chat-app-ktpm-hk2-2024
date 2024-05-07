package hcmut.group2.project.chatapp.usermanager.dto;

import lombok.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class PendingFriendDto {
    private Integer id;
    private String username;
    private String phoneNumber;
    private String avatarUrl;
}