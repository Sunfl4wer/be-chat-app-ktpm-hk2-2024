package hcmut.group2.project.chatapp.services;

import com.amazonaws.services.globalaccelerator.model.CreateAcceleratorRequest;
import hcmut.group2.project.chatapp.entities.Conversation;
import hcmut.group2.project.chatapp.repositories.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public Conversation createConversation(CreateAcceleratorRequest request) {
        return conversationRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(Conversation::getConversationId)
                .or(() -> {
                    if (createIfNotExists) {
                        String conversationId = createPersonalChat(senderId, recipientId);
                        return Optional.of(conversationId);
                    }

                    return Optional.empty();
                });
    }

    private String createPersonalChat(String senderId, String recipientId) {
        var conversationId = String.format("%s_%s", senderId, recipientId);

        Conversations senderRecipient = new Conversations(senderId, recipientId, conversationId);
        Conversations recipientSender = new Conversations(recipientId, senderId, conversationId);

        conversationRepository.save(senderRecipient);
        conversationRepository.save(recipientSender);

        return conversationId;
    }

}
