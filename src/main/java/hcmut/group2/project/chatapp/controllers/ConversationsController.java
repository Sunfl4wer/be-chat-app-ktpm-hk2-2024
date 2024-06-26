package hcmut.group2.project.chatapp.controllers;

import hcmut.group2.project.chatapp.controllers.request.CreateConversationRequest;
import hcmut.group2.project.chatapp.controllers.request.CreateConversationResponse;
import hcmut.group2.project.chatapp.controllers.request.SendMessageRequest;
import hcmut.group2.project.chatapp.dto.ConversationEventDto;
import hcmut.group2.project.chatapp.entities.Conversation;
import hcmut.group2.project.chatapp.entities.Message;
import hcmut.group2.project.chatapp.services.ConversationService;
import hcmut.group2.project.chatapp.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConversationsController implements JWTAuthController {

    @Autowired
    private final ConversationService conversationService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    private final MessageService messageService;

    @MessageMapping("/sendMessage")
    public void recordMessage(@Payload SendMessageRequest message) {
        Message savedMessage = messageService.record(message);
        String destination = "/topic/conversations/" + message.getConversationId();
        simpMessagingTemplate.convertAndSend(destination, ConversationEventDto.builder()
                .type("new_message")
                .data(savedMessage)
                .build());
    }

    @PostMapping("/conversations")
    public ResponseEntity<CreateConversationResponse> createConversation(@RequestBody CreateConversationRequest request) {
        final Conversation conversation = conversationService.createConversation(request);
        return ResponseEntity.ok(CreateConversationResponse.builder()
                        .creator(request.getCreator())
                        .id(conversation.getId())
                        .participants(request.getParticipants())
                        .name(request.getName())
                .build());
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<Conversation>> getConversations(@RequestParam(name = "userId") Long userId) {
        final List<Conversation> conversations = conversationService.getConversations(userId);
        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/conversation/{conversationId}/messages")
    public ResponseEntity<Page<Message>> findConversationMessage(@PathVariable Long conversationId,
                                                                 Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10);
        }
        return ResponseEntity.ok(messageService.getMessages(conversationId, pageable));
    }
}
