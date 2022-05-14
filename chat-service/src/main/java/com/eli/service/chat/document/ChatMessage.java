package com.eli.service.chat.document;

import lombok.Data;

@Data
public class ChatMessage {

	private String sender;
	private String content;
	private MessageType type;
}
