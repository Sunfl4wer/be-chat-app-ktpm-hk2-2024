package hcmut.group2.project.chatapp.usermanager.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "is_logged_out")
    private boolean loggedOut;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ChatUser user;

    public boolean isLoggedOut() {
        return loggedOut;
    }
}