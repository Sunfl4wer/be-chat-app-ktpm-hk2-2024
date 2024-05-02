package hcmut.group2.project.chatapp.usermanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hcmut.group2.project.chatapp.usermanager.entities.RefreshToken;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {


    @Query("""
        select t from RefreshToken t inner join ChatUser u on t.user.id = u.id
    where t.user.id = :userId and t.loggedOut = false""")
    List<RefreshToken> findAllTokensByUser(Integer userId);

    Optional<RefreshToken> findByToken(String token);
}