package hcmut.group2.project.chatapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class MsgNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String senderId;
    private String recipientId;
    private String content;

}
