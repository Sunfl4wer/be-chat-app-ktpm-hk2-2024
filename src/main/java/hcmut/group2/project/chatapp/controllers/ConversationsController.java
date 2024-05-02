package hcmut.group2.project.chatapp.controllers;

import hcmut.group2.project.chatapp.entities.Messages;
import hcmut.group2.project.chatapp.entities.MsgNotification;
import hcmut.group2.project.chatapp.services.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ConversationsController {

    @Autowired
    private final SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private final MessagesService messagesService;

    @MessageMapping("/conversations")
    public void processMessage(@Payload Messages messages) {
        Messages savedMessages = messagesService.save(messages);
        String recipientId = messages.getRecipientId();
        messagingTemplate.convertAndSendToUser(
                recipientId,
                "/queue/messages",
                new MsgNotification(
                        savedMessages.getId(),
                        savedMessages.getSenderId(),
                        savedMessages.getRecipientId(),
                        savedMessages.getTextBody()
                )
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<Messages>> findConversationMessage(@PathVariable String senderId, @PathVariable String recipientId) {
        return ResponseEntity.ok(messagesService.findConversationMessages(senderId, recipientId));
    }

    @MessageMapping("/conversations.sendMessage")
    @SendTo("/topic/public")
    public Messages sendMessage(@Payload Messages messages) {
        return messages;
    }

    @MessageMapping("/conversations.addUser")
    @SendTo("/topic/public")
    public Messages addUser(@Payload Messages messages, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", messages.getSenderId());
        return messages;
    }

}
