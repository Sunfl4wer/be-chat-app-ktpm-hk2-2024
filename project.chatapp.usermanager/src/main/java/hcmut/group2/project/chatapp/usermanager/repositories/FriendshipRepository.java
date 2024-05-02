package hcmut.group2.project.chatapp.usermanager.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hcmut.group2.project.chatapp.usermanager.entities.Friendship;
import hcmut.group2.project.chatapp.usermanager.entities.keys.FriendshipId;
import hcmut.group2.project.chatapp.usermanager.enums.FriendshipStatus;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipId> {
    @Query("SELECT f FROM Friendship f WHERE f.user.id = :userId OR f.friend.id = :userId")
    List<Friendship> findAllByUserId(Integer userId);

    @Query("SELECT f FROM Friendship f WHERE (f.user.id = :userId OR f.friend.id = :userId) AND f.status = :status")
    List<Friendship> findAllByUserIdAndStatus(Integer userId, FriendshipStatus status);

    @Query("SELECT f FROM Friendship f WHERE f.friend.id = :friendId AND f.status = :status")
    List<Friendship> findAllByFriendIdAndStatus(Integer friendId, FriendshipStatus status);

    Optional<Friendship> findByIdAndStatus(FriendshipId id, FriendshipStatus status);
}
