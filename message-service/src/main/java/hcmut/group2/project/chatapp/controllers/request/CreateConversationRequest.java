package hcmut.group2.project.chatapp.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateConversationRequest {
    private Long creator;
    private String name;
    private List<Long> participants;
}
