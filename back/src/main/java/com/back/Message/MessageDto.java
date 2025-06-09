package com.back.message;


import lombok.Data;

@Data
public class MessageDto {
    private Long receiverId;
    private String content;
    private Long replyToId;
}