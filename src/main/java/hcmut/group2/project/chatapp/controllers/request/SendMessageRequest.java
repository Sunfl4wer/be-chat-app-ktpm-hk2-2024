package hcmut.group2.project.chatapp.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SendMessageRequest {
    private Long conversationId;
    private String textBody;
    private Long senderId;
    private Long mediaId;
    private Date sendTime;
}
