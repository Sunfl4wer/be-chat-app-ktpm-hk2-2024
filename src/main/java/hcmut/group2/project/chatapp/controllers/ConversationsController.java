package hcmut.group2.project.chatapp.controllers;

import hcmut.group2.project.chatapp.controllers.request.CreateConversationRequest;
import hcmut.group2.project.chatapp.controllers.request.SendMessageRequest;
import hcmut.group2.project.chatapp.entities.Conversation;
import hcmut.group2.project.chatapp.entities.Message;
import hcmut.group2.project.chatapp.entities.MsgNotification;
import hcmut.group2.project.chatapp.services.ConversationService;
import hcmut.group2.project.chatapp.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ConversationsController {

    @Autowired
    private final ConversationService conversationService;
    
    @Autowired
    private final MessageService messageService;

    @MessageMapping("/messages")
    public void recordMessage(@Payload SendMessageRequest message) {
        Message savedMessage = messageService.record(message);
        String recipientId = message.getRecipientId();
        messagingTemplate.convertAndSendToUser(
                recipientId,
                "/queue/messages",
                new MsgNotification(
                        savedMessage.getId(),
                        savedMessage.getSenderId(),
                        savedMessage.getTextBody()
                )
        );
    }

    @PostMapping("/conversations")
    public ResponseEntity<Conversation> createConversation(@RequestBody CreateConversationRequest request) {
        final Conversation conversation = conversationService.createConversation(request);
        return ResponseEntity.ok(conversation);
    }

    @GetMapping("/conversations")
    public ResponseEntity<Page<Conversation>> getConversations(@RequestParam(name = "userId") Long userId,
                                                              Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10);
        }
        final Page<Conversation> conversations = conversationService.getConversation(userId, pageable);
        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/conversation/{conversationId}/messages")
    public ResponseEntity<List<Message>> findConversationMessage(@PathVariable Long conversationId) {
        return ResponseEntity.ok(messageService.findConversationMessages(senderId, recipientId));
    }

    @MessageMapping("/conversations.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        return message;
    }

    @MessageMapping("/conversations.addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSenderId());
        return message;
    }

}
