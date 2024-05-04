package hcmut.group2.project.chatapp.services;

import hcmut.group2.project.chatapp.controllers.request.CreateConversationRequest;
import hcmut.group2.project.chatapp.entities.Conversation;
import hcmut.group2.project.chatapp.entities.Participant;
import hcmut.group2.project.chatapp.repositories.ConversationRepository;
import hcmut.group2.project.chatapp.repositories.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final ParticipantRepository participantRepository;

    public Conversation createConversation(CreateConversationRequest request) {
        final Conversation conversation = Conversation.builder()
                .creatorId(request.getCreator())
                .name(request.getName())
                .build();
        final Conversation savedConversation = conversationRepository.save(conversation);
        request.getParticipants().forEach(userId -> {
            Participant participant = Participant.builder()
                    .conversationId(savedConversation.getId())
                    .userId(userId)
                    .build();
            participantRepository.save(participant);
        });
        return savedConversation;
    }

    public List<Conversation> getConversations(Long userId) {
       return conversationRepository.findLatestConversationsForUser(userId);
    }
}
