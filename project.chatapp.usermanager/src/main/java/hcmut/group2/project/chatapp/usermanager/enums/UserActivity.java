package hcmut.group2.project.chatapp.usermanager.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserActivity {
    @JsonProperty("online")
    ONLINE("online"),
    @JsonProperty("away")
    AWAY("away"),
    @JsonProperty("offline")
    OFFLINE("offline");

    private String activityString;
}
