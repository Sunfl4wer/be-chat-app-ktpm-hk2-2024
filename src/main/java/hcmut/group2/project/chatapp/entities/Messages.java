package hcmut.group2.project.chatapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    @Column(name = "conversation_id", nullable = false)
    private String conversationId;

    @Column(name = "text_body", nullable = false)
    private String textBody;

    @Column(name = "sender_id", nullable = false)
    private String senderId;

    @Column(name = "recipient_id", nullable = false)
    private String recipientId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "edited", nullable = false)
    private Boolean edited;

    @Column(name = "status", nullable = false)
    private MessageStatus status;

    @Column(name = "send_time", nullable = false)
    private Date sendTime;

    @Column(name = "modify_time", nullable = false)
    private Date modifyTime;
}

