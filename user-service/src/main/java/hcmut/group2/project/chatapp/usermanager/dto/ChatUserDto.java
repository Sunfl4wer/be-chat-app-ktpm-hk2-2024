package hcmut.group2.project.chatapp.usermanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
// import java.util.Set;

// import hcmut.group2.project.chatapp.entities.Friendship;
import hcmut.group2.project.chatapp.usermanager.enums.UserActivity;
import hcmut.group2.project.chatapp.usermanager.enums.UserRole;
import hcmut.group2.project.chatapp.usermanager.enums.UserStatus;
import lombok.AllArgsConstructor;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserDto {
    private Integer id;
    private String username;
    private String password;
    private UserRole role;
    private UserStatus status;
    private UserActivity activity;
    private String phoneNumber;
    private String emailAddress;
    private String avatarUrl;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDateTime creationDatetime;
    private LocalDateTime modificationDatetime;
    // private Set<Friendship> friendships;
    // private Set<Friendship> friendOf;
}
