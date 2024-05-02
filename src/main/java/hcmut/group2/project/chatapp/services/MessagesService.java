package hcmut.group2.project.chatapp.services;

import hcmut.group2.project.chatapp.entities.Messages;
import hcmut.group2.project.chatapp.repositories.MessagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessagesService {

    private final MessagesRepository messagesRepository;
    private final ConversationsService conversationsService;

    public Messages save(Messages messages) {
        var conversationId = conversationsService
                .getConversationId(messages.getSenderId(), messages.getRecipientId(), true)
                .orElseThrow();

        messages.setId(conversationId);
        messagesRepository.save(messages);
        return messages;
    }

    public List<Messages> findConversationMessages(String senderId, String recipientId) {
        var conversationId = conversationsService.getConversationId(senderId, recipientId, false);
        return conversationId.map(messagesRepository::findByConversationId).orElse(new ArrayList<>());
    }

}
