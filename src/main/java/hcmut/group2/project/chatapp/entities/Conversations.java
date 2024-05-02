package hcmut.group2.project.chatapp.entities;

import hcmut.group2.project.chatapp.entities.key.ConversationId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(ConversationId.class)
@Table(name = "conversations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Conversations {

    @Id
    @Column(name = "sender_id", nullable = false)
    private String senderId;

    @Id
    @Column(name = "recipient_id", nullable = false)
    private String recipientId;

    @Id
    @Column(name = "message_id", nullable = false)
    private String messageId;

    @Id
    @Column(name = "conversation_id", nullable = false)
    private String conversationId;

    public Conversations(String senderId, String recipientId, String conversationId) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.conversationId = conversationId;
    }
}
