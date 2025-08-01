package com.back.message;

import lombok.Data;
import org.springframework.data.domain.Page;
import java.util.List;

@Data
public class ChatPage {
    private List<MessageDto> messages;
    private int totalPages;
    private Long totalMessages;

    public static ChatPage fromPage(Page<MessageDto> page) {
        ChatPage chatPage = new ChatPage();
        chatPage.setMessages(page.getContent());
        chatPage.setTotalPages(page.getTotalPages());
        chatPage.setTotalMessages(page.getTotalElements());
        return chatPage;
    }

    public static ChatPage fromList(List<MessageDto> messages) {
        ChatPage chatPage = new ChatPage();
        chatPage.setMessages(messages);
        chatPage.setTotalPages(1);
        chatPage.setTotalMessages((long) messages.size());
        return chatPage;
    }
}
