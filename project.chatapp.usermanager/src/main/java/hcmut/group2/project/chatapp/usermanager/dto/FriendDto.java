package hcmut.group2.project.chatapp.usermanager.dto;

import lombok.*;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendDto {
    private Integer id;
    private String username;
    private String phoneNumber;
    private String avatarUrl;
}