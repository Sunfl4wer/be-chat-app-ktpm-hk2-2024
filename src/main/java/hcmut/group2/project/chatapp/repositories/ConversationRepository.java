package hcmut.group2.project.chatapp.repositories;

import hcmut.group2.project.chatapp.entities.Conversation;
import hcmut.group2.project.chatapp.entities.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Long> {

    @Query(
            value = "SELECT c.*, MAX(m.created_at) AS latest_message_timestamp" +
                    "FROM conversations c" +
                    "JOIN messages m ON c.id = m.conversation_id" +
                    "WHERE c.user_id = :userId" +
                    "GROUP BY c.id" +
                    "ORDER BY latest_message_timestamp DESC",
            nativeQuery = true
    )
    List<Conversation> findLatestConversationsForUser(Long userId);
}
