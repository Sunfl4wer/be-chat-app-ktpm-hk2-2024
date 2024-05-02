package hcmut.group2.project.chatapp.repositories;

import hcmut.group2.project.chatapp.entities.Messages;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends CrudRepository<Messages, String> {
    List<Messages> findByConversationId(String id);
}
