package com.back.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.back.user.CustomUserDetails;
import com.back.user.User;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDto> sendMessage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody MessageDto request) {
            Message message = messageService.sendMessage(
                    userDetails.getUser(),
                    new User() {{ setId(request.getReceiverId()); }},
                    request.getContent(),
                    request.getReplyToId()
            );
        return ResponseEntity.ok(message.toDto());
    }

    @GetMapping("/inbox/all")
    public ResponseEntity<List<MessageDto>> getInbox(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Message> messages = messageService.getInbox(userDetails.getUser().getId());
        return ResponseEntity.ok(Message.toDtoList(messages));
    }
    //Wybierz sobie jak wolisz brac te wiadomosci na froncie, albo mi napisz jak chcesz, żebym zrobił ci backend
    @GetMapping("/sent/all")
    public ResponseEntity<List<MessageDto>> getSentMessages(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Message> messages = messageService.getSentMessages(userDetails.getUser().getId());
        return ResponseEntity.ok(Message.toDtoList(messages));
    }

    @GetMapping("/chatlist")
    public ResponseEntity<List<Long>> getChatList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Long> chatList = messageService.getChatList(userDetails.getUser().getId());
        return ResponseEntity.ok(chatList);
    }
    @GetMapping("/chatlist/{userId}")
    public ResponseEntity<ChatPage> getChatMessages(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        ChatPage messages = messageService.getChatMessages(userDetails.getUser().getId(), userId, page, size);
        return ResponseEntity.ok(messages);
    }
}