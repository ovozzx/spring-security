package com.ktdsuniversity.edu.websocket.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger("CHAT");
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		logger.info("받은 대화: {}", payload);
		
		TextMessage msg = new TextMessage("보냅니다~~~~~");
		if (session.isOpen()) {
			synchronized (session) {
				session.sendMessage(msg);
			}
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("받은 대화: {}", "나갔습니다.");
	}
	
}
