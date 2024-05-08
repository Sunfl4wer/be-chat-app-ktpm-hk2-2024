package hcmut.group2.project.chatapp.controllers.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateConversationResponse {
    private Long id;
    private Long creator;
    private String name;
    private List<Long> participants;
}
