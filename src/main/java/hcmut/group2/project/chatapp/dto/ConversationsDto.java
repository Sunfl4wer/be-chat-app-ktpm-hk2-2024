package hcmut.group2.project.chatapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversationsDto {
    private String senderId;
    private String recipientId;
    private String messageId;
    private String conversationId;
}
