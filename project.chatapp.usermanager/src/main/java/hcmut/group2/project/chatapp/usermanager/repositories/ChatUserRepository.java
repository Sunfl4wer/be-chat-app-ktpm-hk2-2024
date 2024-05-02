package hcmut.group2.project.chatapp.usermanager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmut.group2.project.chatapp.usermanager.entities.ChatUser;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, Integer> {
    Optional<ChatUser> findByUsername(String username);
    Optional<ChatUser> findByPhoneNumber(String phoneNumber);
    void deleteByPhoneNumber(String phoneNumber);
}
