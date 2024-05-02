package hcmut.group2.project.chatapp;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import hcmut.group2.project.chatapp.usermanager.entities.ChatUser;
import hcmut.group2.project.chatapp.usermanager.enums.UserActivity;
import hcmut.group2.project.chatapp.usermanager.enums.UserRole;
import hcmut.group2.project.chatapp.usermanager.enums.UserStatus;
import hcmut.group2.project.chatapp.usermanager.repositories.ChatUserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTests {
 
    @Autowired
    private TestEntityManager entityManager;
     
    @Autowired
    private ChatUserRepository repo;

    private ChatUser testUser = ChatUser.builder().phoneNumber("0913567891")
                                                .username("testUserName01")
                                                .password("testPassword01")
                                                .role(UserRole.ADMIN)
                                                .status(UserStatus.ACTIVE)
                                                .activity(UserActivity.OFFLINE)
                                                .creationDatetime(LocalDateTime.now())
                                                .modificationDatetime(LocalDateTime.now())
                                                .birthDate(LocalDate.parse("1996-05-08"))
                                                .firstName("Firsty")
                                                .lastName("Lasty")
                                                .emailAddress("test@gmail.com")
                                                .build();
     
    // test methods go below
    @Test
    public void testCreateUser() {        
        ChatUser savedUser = repo.save(testUser);
        
        ChatUser existUser = entityManager.find(ChatUser.class, savedUser.getId());
        
        assertThat(testUser.getPhoneNumber()).isEqualTo(existUser.getPhoneNumber());
    }
}
