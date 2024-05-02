package hcmut.group2.project.chatapp.entities.key;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConversationId implements Serializable {
    private String senderId;
    private String recipientId;
    private String messageId;
    private String conversationId;
}
