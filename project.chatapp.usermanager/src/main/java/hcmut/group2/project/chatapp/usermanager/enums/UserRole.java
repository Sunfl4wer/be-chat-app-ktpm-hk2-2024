package hcmut.group2.project.chatapp.usermanager.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    @JsonProperty("user")
    USER("user"),
    @JsonProperty("admin")
    ADMIN("admin");

    @Getter
    private String roleString;
}
