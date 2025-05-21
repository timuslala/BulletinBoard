package com.back.Message;


import lombok.Data;

@Data
public class MessageDto {
    private Long receiverId;
    private String content;
    private Long replyToId;
}