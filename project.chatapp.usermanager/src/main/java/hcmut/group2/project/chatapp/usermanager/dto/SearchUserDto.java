package hcmut.group2.project.chatapp.usermanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import lombok.AllArgsConstructor;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserDto {
    private Integer id;
    private String username;
    private String phoneNumber;
    private String emailAddress;
    private String avatarUrl;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
