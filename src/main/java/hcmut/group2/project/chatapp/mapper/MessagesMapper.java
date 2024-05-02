package hcmut.group2.project.chatapp.mapper;

import hcmut.group2.project.chatapp.dto.MessagesDto;
import hcmut.group2.project.chatapp.entities.Messages;

public class MessagesMapper {

    public static MessagesDto mapToMessagesDto(Messages messages) {
        return new MessagesDto(
                messages.getId(),
                messages.getConversationId(),
                messages.getTextBody(),
                messages.getSenderId(),
                messages.getRecipientId(),
                messages.getImageUrl(),
                messages.getVideoUrl(),
                messages.getEdited(),
                messages.getStatus(),
                messages.getSendTime(),
                messages.getModifyTime()
        );
    }

    public static Messages mapToMessages(MessagesDto messagesDto) {
        return new Messages(
                messagesDto.getId(),
                messagesDto.getConversationId(),
                messagesDto.getTextBody(),
                messagesDto.getSenderId(),
                messagesDto.getRecipientId(),
                messagesDto.getImageUrl(),
                messagesDto.getVideoUrl(),
                messagesDto.getEdited(),
                messagesDto.getStatus(),
                messagesDto.getSendTime(),
                messagesDto.getModifyTime()
        );
    }

}
