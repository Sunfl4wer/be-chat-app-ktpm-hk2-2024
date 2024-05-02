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
    private String conversationId;
    private String textBody;
    private String senderId;
    private Long mediaId;
    private Date sendTime;
}
