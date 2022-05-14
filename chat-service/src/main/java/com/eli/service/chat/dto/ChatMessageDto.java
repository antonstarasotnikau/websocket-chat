package com.eli.service.chat.dto;

import java.time.LocalDateTime;

public record ChatMessageDto(String senderName, String recipientName, String message, LocalDateTime timestamp) {

}
