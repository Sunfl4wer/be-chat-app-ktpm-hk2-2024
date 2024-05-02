package hcmut.group2.project.chatapp.dto;

import hcmut.group2.project.chatapp.entities.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessagesDto {

    private String id;
    private String conversationId;
    private String textBody; // content
    private String senderId;
    private String recipientId;
    private String imageUrl;
    private String videoUrl;
    private Boolean edited;
    private MessageStatus status;
    private Date sendTime;
    private Date modifyTime;

}
