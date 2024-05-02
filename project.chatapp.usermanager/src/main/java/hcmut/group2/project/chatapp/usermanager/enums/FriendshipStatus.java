package hcmut.group2.project.chatapp.usermanager.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FriendshipStatus {
    @JsonProperty("pending")
    PENDING("pending"),
    @JsonProperty("accepted")
    ACCEPTED("accepted"),
    @JsonProperty("declined")
    DECLINED("declined"),
    @JsonProperty("blocked")
    BLOCKED("blocked");

    private String statusString;
}
