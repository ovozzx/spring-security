package com.ktdsuniversity.edu.websocket.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.ktdsuniversity.edu.member.vo.MemberVO;
import com.ktdsuniversity.edu.websocket.message.ChatMessage;

@Component
public class ChatHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger("CHAT");
	
	private Gson gson;
	private List<WebSocketSession> sessions;
	private Map<WebSocketSession, MemberVO> identify;
	
	// [2025-10-20] Chatting Room 보관
	private Map<String, List<WebSocketSession>> rooms;
	
	public ChatHandler() {
		this.gson = new Gson();
		this.sessions = new ArrayList<>();
		this.identify = new HashMap<>();
		
		// [2025-10-20] Chatting Room 보관
		this.rooms = new HashMap<>();
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		this.sessions.add(session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		
		Gson gson = new Gson();
		ChatMessage chatMessage = gson.fromJson(payload, ChatMessage.class);
		if (this.matchAction(chatMessage, "JOIN")) {
			MemberVO member = new MemberVO();
			member.setEmail(chatMessage.getUserEmail());
			member.setName(chatMessage.getUsername());
			this.identify.put(session, member);
		}
		else if (this.matchAction(chatMessage, "INVITE")) {
			MemberVO member = this.identify.get(session);
			chatMessage.setUserEmail(member.getEmail());
			chatMessage.setUsername(member.getName());
			
			WebSocketSession targetSession = this.findSession(chatMessage.getTarget());
			
			if (targetSession == null || !targetSession.isOpen()) {
				chatMessage.setAction("INVITE_FAIL");
				chatMessage.setMessage(chatMessage.getTarget() + " 사용자는 로그인 하지 않았습니다.");
				this.sendMessage(session, chatMessage);
				return;
			}
			else if (targetSession != null) {
				this.sendMessage(targetSession, chatMessage);
				return;
			}
		}
		else if (this.matchAction(chatMessage, "INVITE_OK")) {
			MemberVO member = this.identify.get(session);
			chatMessage.setUserEmail(member.getEmail());
			chatMessage.setUsername(member.getName());
			
			WebSocketSession targetSession = this.findSession(chatMessage.getTarget());
			
			// [2025-10-20] Chatting Room 보관 시작
			MemberVO me = this.identify.get(session);
			String roomName = "ROOM_" + me.getEmail() + "_" + chatMessage.getTarget();
			this.rooms.put(roomName, List.of(session, targetSession));
			chatMessage.setRoom(roomName);
			// [2025-10-20] Chatting Room 보관 끝
			
			if (targetSession != null) {
				// [2025-10-20] Chatting Room 보관
				this.sendMessage(session, chatMessage);
				
				this.sendMessage(targetSession, chatMessage);
				return;
			}
		}
		else if (this.matchAction(chatMessage, "INVITE_DENY")) {
			MemberVO member = this.identify.get(session);
			chatMessage.setUserEmail(member.getEmail());
			chatMessage.setUsername(member.getName());
			
			WebSocketSession targetSession = this.findSession(chatMessage.getTarget());
			
			if (targetSession != null) {
				this.sendMessage(targetSession, chatMessage);
				return;
			}
		}
		// [2025-10-20] Chatting Room 보관
		else if (this.matchAction(chatMessage, "ONE-TO-ONE-CHAT")) {
			MemberVO member = this.identify.get(session);
			chatMessage.setUserEmail(member.getEmail());
			chatMessage.setUsername(member.getName());

			List<WebSocketSession> roomUsers = this.rooms.get(chatMessage.getRoom());
			
			if (roomUsers != null) {
				for (WebSocketSession user : roomUsers) {
					this.sendMessage(user, chatMessage);
				}
				return;
			}
		}
		// [2025-10-20] Chatting Room 보관
		else {
			TextMessage msg = new TextMessage(payload);
			
			for (WebSocketSession eachUser: this.sessions) {
				this.sendMessage(eachUser, msg);
			}
		}
		
		
		logger.info("받은 대화: {}", payload);
	}
	
	private boolean matchAction(ChatMessage chatMessage, String action) {
		return chatMessage.getAction() != null && 
				chatMessage.getAction().equals(action);
	}
	
	private WebSocketSession findSession(String email) {
		return this.identify.entrySet()
				.stream()
				.filter(entry -> entry.getValue().getEmail().equals(email))
				.map(entry -> entry.getKey())
				.findFirst()
				.orElse(null);
	}
	
	private void sendMessage(WebSocketSession session, TextMessage textMessage) {
		if (session.isOpen()) {
			synchronized (session) {
				try {
					session.sendMessage(textMessage);
				} catch (IOException e) {}
			}
		}
	}
	
	private void sendMessage(WebSocketSession session, ChatMessage chatMessage) {
		if (session.isOpen()) {
			TextMessage message = new TextMessage( this.gson.toJson(chatMessage) );
			synchronized (session) {
				try {
					session.sendMessage(message);
				} catch (IOException e) {}
			}
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("받은 대화: {}", "나갔습니다.");
		this.sessions.remove(session);
		
		MemberVO member = this.identify.remove(session);
		
		ChatMessage message = new ChatMessage(
					member.getName(), 
					member.getEmail(), 
					member.getName() + "님이 접속을 종료했습니다.");
		
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message);
		
		TextMessage msg = new TextMessage(jsonMessage);
		
		for (WebSocketSession eachUser: this.sessions) {
			if (eachUser.isOpen()) {
				synchronized (eachUser) {
					eachUser.sendMessage(msg);
				}
			}
		}
		
	}
	
}
