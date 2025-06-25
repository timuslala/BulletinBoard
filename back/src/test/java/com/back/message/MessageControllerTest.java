package com.back.message;

import com.back.user.CustomUserDetails;
import com.back.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MessageService messageService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private CustomUserDetails getMockUserDetails(Long id) {
        User user = new User();
        user.setId(id);
        return new CustomUserDetails(user);
    }

    @Test
    void sendMessage() throws Exception {
        // given
        CustomUserDetails userDetails = getMockUserDetails(1L);

        MessageDto request = new MessageDto();
        request.setReceiverId(2L);
        request.setContent("Hello!");
        request.setReplyToId(10L);

        Message message = new Message();
        User sender = new User();
        sender.setId(1L);
        User receiver = new User();
        receiver.setId(2L);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent("Hello!");
        Message replyTo = new Message();
        replyTo.setId(10L);
        message.setReplyTo(replyTo);

        org.mockito.Mockito.when(messageService.sendMessage(
                org.mockito.Mockito.eq(sender),
                org.mockito.Mockito.eq(receiver),
                org.mockito.Mockito.eq("Hello!"),
                org.mockito.Mockito.eq(10L)
        )).thenReturn(message);

        // when & then
        mockMvc.perform(post("/api/messages")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.receiverId").value(2L))
            .andExpect(jsonPath("$.content").value("Hello!"))
            .andExpect(jsonPath("$.replyToId").value(10L));
    }

    @Test
    void getInbox() throws Exception {
        // given
        CustomUserDetails userDetails = getMockUserDetails(1L);

        Message message = new Message();
        User receiver = new User();
        receiver.setId(1L);
        message.setReceiver(receiver);
        message.setContent("Inbox message");
        message.setReplyTo(null);

        org.mockito.Mockito.when(messageService.getInbox(1L)).thenReturn(List.of(message));

        // when & then
        mockMvc.perform(get("/api/messages/inbox/all")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(userDetails)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0].receiverId").value(1L))
            .andExpect(jsonPath("$[0].content").value("Inbox message"))
            .andExpect(jsonPath("$[0].replyToId").doesNotExist());
    }

    @Test
    void getSentMessages() throws Exception {
        // given
        CustomUserDetails userDetails = getMockUserDetails(1L);

        Message message = new Message();
        User receiver = new User();
        receiver.setId(2L);
        message.setReceiver(receiver);
        message.setContent("Sent message");
        message.setReplyTo(null);

        org.mockito.Mockito.when(messageService.getSentMessages(1L)).thenReturn(List.of(message));

        // when & then
        mockMvc.perform(get("/api/messages/sent/all")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(userDetails)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0].receiverId").value(2L))
            .andExpect(jsonPath("$[0].content").value("Sent message"))
            .andExpect(jsonPath("$[0].replyToId").doesNotExist());
    }

    @Test
    void getChatList() throws Exception {
        // given
        CustomUserDetails userDetails = getMockUserDetails(1L);

        org.mockito.Mockito.when(messageService.getChatList(1L)).thenReturn(List.of(2L, 3L));

        // when & then
        mockMvc.perform(get("/api/messages/chatlist")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(userDetails)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0]").value(2L))
            .andExpect(jsonPath("$[1]").value(3L));
    }

    @Test
    void getChatMessages() throws Exception {
        // given
        CustomUserDetails userDetails = getMockUserDetails(1L);

        ChatPage chatPage = new ChatPage();
        Message message = new Message();
        message.setContent("Chat message");
        MessageDto messageDto = new MessageDto();
        messageDto.setContent("Chat message");
        chatPage.setMessages(List.of(messageDto));
        chatPage.setTotalPages(5);
        chatPage.setTotalMessages(10L);

        org.mockito.Mockito.when(messageService.getChatMessages(1L, 123L, 123, 123)).thenReturn(chatPage);

        // when & then
        mockMvc.perform(get("/api/messages/chatlist/{userId}", 123L)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user(userDetails))
                .param("page", "123")
                .param("size", "123"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.messages[0].content").value("Chat message"))
            .andExpect(jsonPath("$.totalPages").value(5))
            .andExpect(jsonPath("$.totalMessages").value(10));
    }
}