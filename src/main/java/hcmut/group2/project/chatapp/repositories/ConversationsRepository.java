package hcmut.group2.project.chatapp.repositories;

import hcmut.group2.project.chatapp.entities.Conversations;
import hcmut.group2.project.chatapp.entities.key.ConversationId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationsRepository  extends CrudRepository<Conversations, ConversationId> {

    Optional<Conversations> findBySenderIdAndRecipientId(String senderId, String recipientId);

}
