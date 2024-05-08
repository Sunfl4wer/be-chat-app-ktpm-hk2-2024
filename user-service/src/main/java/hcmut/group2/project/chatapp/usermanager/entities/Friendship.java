package hcmut.group2.project.chatapp.usermanager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import hcmut.group2.project.chatapp.usermanager.entities.keys.FriendshipId;
import hcmut.group2.project.chatapp.usermanager.enums.FriendshipStatus;

@Table(name = "friendships")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {

    @EmbeddedId
    private FriendshipId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private ChatUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("friendId")
    @JoinColumn(name = "friend_id")
    private ChatUser friend;

    @Column(name = "status", nullable = false)
    private FriendshipStatus status;

    @Column(name = "creation_datetime", nullable = false)
    private LocalDateTime creationDatetime;

    @Builder
    public Friendship(ChatUser requestor, ChatUser acceptor) {
        this.user = requestor;
        this.friend = acceptor;
        this.status = FriendshipStatus.PENDING;
        this.creationDatetime = LocalDateTime.now();
        this.id = new FriendshipId(requestor.getId(), acceptor.getId());

    }
}
