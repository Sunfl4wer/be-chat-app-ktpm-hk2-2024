package hcmut.group2.project.chatapp.services;

import hcmut.group2.project.chatapp.controllers.request.CreateConversationRequest;
import hcmut.group2.project.chatapp.entities.Conversation;
import hcmut.group2.project.chatapp.repositories.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public Conversation createConversation(CreateConversationRequest request) {
        Conversation conversation = Conversation.builder()
                .creatorId(request.getCreator())
                .name(request.getName())
                .build();
        return conversationRepository.save(conversation);
    }

    public List<Conversation> getConversations(Long userId) {
       return conversationRepository.findLatestConversationsForUser(userId);
    }
}
