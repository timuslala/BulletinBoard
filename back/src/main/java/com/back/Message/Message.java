package com.back.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.back.user.User;
@Data
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime sentAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "reply_to_id")
    private Message replyTo;
    
    public MessageDto toDto() {
        MessageDto dto = new MessageDto();
        dto.setReceiverId(this.receiver != null ? this.receiver.getId() : null);
        dto.setContent(this.content);
        dto.setReplyToId(this.replyTo != null ? this.replyTo.getId() : null);
        return dto;
    }
    public static List<MessageDto> toDtoList(List<Message> messages) {
        return messages.stream()
                .map(Message::toDto)
                .toList();
    }
}
