package hcmut.group2.project.chatapp.repositories;

import hcmut.group2.project.chatapp.entities.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends CrudRepository<Message, String> {
    List<Message> findByConversationId(String id);
}
