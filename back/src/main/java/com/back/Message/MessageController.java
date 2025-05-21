package com.back.Message;

import com.back.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(
            @AuthenticationPrincipal User user,
            @RequestBody MessageDto request) {
        Message message = messageService.sendMessage(
                user,
                new User() {{ setId(request.getReceiverId()); }},
                request.getContent(),
                request.getReplyToId()
        );
        return ResponseEntity.ok(message);
    }

    @GetMapping("/inbox")
    public ResponseEntity<List<Message>> getInbox(@AuthenticationPrincipal User user) {
        List<Message> messages = messageService.getInbox(user.getId());
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/sent")
    public ResponseEntity<List<Message>> getSentMessages(@AuthenticationPrincipal User user) {
        List<Message> messages = messageService.getSentMessages(user.getId());
        return ResponseEntity.ok(messages);
    }
}