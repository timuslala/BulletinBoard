package com.back.Message;

import lombok.Data;
import org.springframework.data.domain.Page;
import java.util.List;

@Data
public class ChatPage {
    private List<Message> messages;
    private int totalPages;
    private Long totalMessages;

    public static ChatPage fromPage(Page<Message> page) {
        ChatPage chatPage = new ChatPage();
        chatPage.setMessages(page.getContent());
        chatPage.setTotalPages(page.getTotalPages());
        chatPage.setTotalMessages(page.getTotalElements());
        return chatPage;
    }

    public static ChatPage fromList(List<Message> messages) {
        ChatPage chatPage = new ChatPage();
        chatPage.setMessages(messages);
        chatPage.setTotalPages(1);
        chatPage.setTotalMessages((long) messages.size());
        return chatPage;
    }
}
