package hcmut.group2.project.chatapp.repositories;

import hcmut.group2.project.chatapp.entities.Conversation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Long> {

    Optional<Conversation> findBySenderIdAndRecipientId(String senderId, String recipientId);

}
