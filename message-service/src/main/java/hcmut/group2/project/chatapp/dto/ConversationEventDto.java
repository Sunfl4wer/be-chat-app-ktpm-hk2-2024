package hcmut.group2.project.chatapp.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversationEventDto {
    private String type;
    private Object data;
}
