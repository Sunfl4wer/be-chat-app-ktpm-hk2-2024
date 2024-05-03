package hcmut.group2.project.chatapp.services;

import hcmut.group2.project.chatapp.controllers.request.SendMessageRequest;
import hcmut.group2.project.chatapp.entities.Message;
import hcmut.group2.project.chatapp.entities.MessageStatus;
import hcmut.group2.project.chatapp.repositories.MessagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessagesRepository messagesRepository;

    public Message record(SendMessageRequest message) {
        final Message msg = Message.builder()
                .conversationId(message.getConversationId())
                .status(MessageStatus.DELIVERED)
                .mediaId(message.getMediaId())
                .textBody(message.getTextBody())
                .senderId(message.getSenderId())
                .sendTime(convertToLocalDateTime(message.getSendTime()))
                .edited(false)
                .build();
        return messagesRepository.save(msg);
    }

    public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public Page<Message> getMessages(Long conversationId, Pageable pageable) {
        return messagesRepository.findByConversationIdOrderByCreatedAt(conversationId, pageable);
    }
}
