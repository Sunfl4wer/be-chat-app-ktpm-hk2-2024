package hcmut.group2.project.chatapp.services;

import hcmut.group2.project.chatapp.entities.Conversations;
import hcmut.group2.project.chatapp.repositories.ConversationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationsService {

    private final ConversationsRepository conversationsRepository;

    public Optional<String> getConversationId(String senderId, String recipientId, boolean createIfNotExists) {
        return conversationsRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(Conversations::getConversationId)
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

        conversationsRepository.save(senderRecipient);
        conversationsRepository.save(recipientSender);

        return conversationId;
    }

}
