package com.back.Message;

import org.springframework.data.domain.Page;
import java.util.List;

public class ChatPage {
    public List<Message> messages;
    public int totalPages;
    public Long totalMessages;

    public static ChatPage fromPage (Page<Message> page) {
        ChatPage chatPage = new ChatPage();
        chatPage.messages = page.getContent();
        chatPage.totalPages = page.getTotalPages();
        chatPage.totalMessages = page.getTotalElements();
        return chatPage;
    }

}

