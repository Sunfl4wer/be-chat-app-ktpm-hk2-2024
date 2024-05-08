package hcmut.group2.project.chatapp.usermanager.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hcmut.group2.project.chatapp.usermanager.entities.ChatUser;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, Integer> {
    Optional<ChatUser> findByUsername(String username);
    Optional<ChatUser> findByPhoneNumber(String phoneNumber);
    void deleteByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM ChatUser u WHERE (u.username LIKE %:searchString% OR u.phoneNumber LIKE %:searchString%) AND u.role = UserRole.USER")
    List<ChatUser> findByPartialUsernameOrPhoneNumber(String searchString);
}
