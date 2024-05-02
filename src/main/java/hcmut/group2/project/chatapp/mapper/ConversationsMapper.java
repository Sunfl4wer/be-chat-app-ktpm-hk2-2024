package hcmut.group2.project.chatapp.mapper;

import hcmut.group2.project.chatapp.dto.ConversationsDto;
import hcmut.group2.project.chatapp.entities.Conversations;

public class ConversationsMapper {

    public static ConversationsDto mapToConversationsDto(Conversations conversations) {
        return new ConversationsDto(
                conversations.getSenderId(),
                conversations.getRecipientId(),
                conversations.getMessageId(),
                conversations.getConversationId()
        );
    }

    public static Conversations mapToConversations(ConversationsDto conversationsDto) {
        return new Conversations(
                conversationsDto.getSenderId(),
                conversationsDto.getRecipientId(),
                conversationsDto.getMessageId(),
                conversationsDto.getConversationId()
        );
    }

}
