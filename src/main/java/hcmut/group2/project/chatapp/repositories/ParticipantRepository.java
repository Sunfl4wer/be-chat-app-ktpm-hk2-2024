package hcmut.group2.project.chatapp.repositories;

import hcmut.group2.project.chatapp.entities.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, Long> {
    List<Participant> findByConversationIdOrderByCreatedAtDesc(Long conversationId);
}
