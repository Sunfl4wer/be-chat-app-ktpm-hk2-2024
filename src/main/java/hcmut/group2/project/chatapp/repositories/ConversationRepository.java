package hcmut.group2.project.chatapp.repositories;

import hcmut.group2.project.chatapp.entities.Conversation;
import hcmut.group2.project.chatapp.entities.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Long> {

    @Query(
            value = "select c.* " +
                    "from conversations c " +
                    "left join messages m " +
                    "on m.conversation_id = c.id " +
                    "where c.id in :conversationIds " +
                    "group by c.id " +
                    "order by max(m.created_at) desc"
            ,nativeQuery = true
    )
    List<Conversation> findLatestConversationsForUser(@Param("conversationIds") List<Long> conversationIds);
}
