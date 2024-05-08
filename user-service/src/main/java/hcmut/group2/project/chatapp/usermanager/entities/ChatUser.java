package hcmut.group2.project.chatapp.usermanager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotEmpty;
// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Pattern;
// import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
// import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hcmut.group2.project.chatapp.usermanager.enums.UserActivity;
import hcmut.group2.project.chatapp.usermanager.enums.UserRole;
import hcmut.group2.project.chatapp.usermanager.enums.UserStatus;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatUser implements UserDetails{

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // @NotNull(message = "Username cannot be null.")
    // @NotEmpty(message = "Username cannot be empty.")
    // @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters long")
    // @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username can only contain alphanumeric characters, dots, dashes, and underscores.")
    @Column(nullable = false, unique=true, length = 15)
    private String username;

    // @NotNull(message = "Password cannot be null.")
    // @NotEmpty(message = "Password cannot be empty.")
    // @Size(min = 8, max = 63, message = "Username must be between 8 and 64 characters long")
    // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", 
    //          message = "Password must be at least 8 characters long, contain at least one number, one uppercase letter, one lowercase letter, and one special character.")
    @Column(nullable = false)
    private String password;

    // @NotNull(message = "Role cannot be null.")
    @Column(nullable = false, name = "user_role")
    private UserRole role;

    // @NotNull(message = "Status cannot be null.")
    @Column(nullable = false)
    private UserStatus status;

    // @NotNull(message = "Activity cannot be null.")
    @Column(nullable = false)
    private UserActivity activity;

    // @NotNull(message = "Phone number cannot be null.")
    // @NotEmpty(message = "Phone number cannot be empty.")
    // @Size(max = 12)
    @Column(nullable = false, unique=true, length = 12, name = "phone_number")
    private String phoneNumber;

    // @Email(message = "Email should be in correct format.")
    // @Size(max = 255)
    @Column(length = 255, name = "email_address")
    private String emailAddress;

    // @Size(max = 255)
    @Column(length = 255, name = "avatar_url")
    private String avatarUrl;

    // @Size(max = 255)
    @Column(length = 255, name = "first_name")
    private String firstName;

    // @Size(max = 255)
    @Column(length = 255, name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    // @NotEmpty(message = "creation_datetime should be not empty.")
    @Column(nullable = false, name = "creation_datetime")
    private LocalDateTime creationDatetime;

    // @NotEmpty(message = "modification_datetime should be not empty.")
    @Column(nullable = false, name = "modification_datetime")
    private LocalDateTime modificationDatetime;

    @OneToMany(mappedBy = "user")
    private Set<Friendship> friendships; // Friends of user

    @OneToMany(mappedBy = "friend")
    private Set<Friendship> friendOf; // Friendships where this user is 'friend

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRoleString()));
    }
}
