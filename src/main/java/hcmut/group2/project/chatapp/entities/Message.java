package hcmut.group2.project.chatapp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "conversation_id", nullable = false)
    private Long conversationId;

    @Column(name = "text_body", nullable = false)
    private String textBody;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "edited", nullable = false)
    private Boolean edited;

    @Column(name = "status", nullable = false)
    private MessageStatus status;

    @Column(name = "send_time", nullable = false)
    private LocalDateTime sendTime;

    @Column(name = "modify_time", nullable = false)
    private LocalDateTime modifyTime;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

