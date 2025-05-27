package com.back.Message;

import com.back.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Message sendMessage(User sender, User receiver, String content, Long replyToId) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        if (replyToId != null) {
            Message replyTo = messageRepository.findById(replyToId)
                    .orElseThrow(() -> new IllegalArgumentException("Reply message not found"));
            message.setReplyTo(replyTo);
        }
        return messageRepository.save(message);
    }

    public List<Message> getInbox(Long userId) {
        return messageRepository.findByReceiverId(userId);
    }

    public List<Message> getSentMessages(Long userId) {
        return messageRepository.findBySenderId(userId);
    }

    public List<User> getChatList(Long userId) {
        List<Message> messages = messageRepository.findBySenderIdOrReceiverId(userId, userId);
        return messages.stream()
                .map(message -> message.getSender().getId().equals(userId) ? message.getReceiver() : message.getSender())
                .distinct()
                .toList();
    }
    public ChatPage getChatMessages(Long userId, Long chatUserId, Integer page, Integer size) {
        if ((page != null && size == null) || (page == null && size != null)) {
            throw new IllegalArgumentException("Both page and size parameters must be provided for pagination");
        }
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        org.springframework.data.domain.Page<Message> pagedMessages = messageRepository.findConversationPaged(userId, chatUserId, pageable);
        return ChatPage.fromPage(pagedMessages);
    }
}