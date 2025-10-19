package com.ktdsuniversity.edu.websocket.message;

public class ChatMessage {

	private String username;
	private String userEmail;
	private String message;
	private String action;
	private String target;

	// [2025-10-20] Chatting Room 보관
	private String room;

	public ChatMessage() {
	}

	public ChatMessage(String username, String userEmail, String message) {
		this.username = username;
		this.userEmail = userEmail;
		this.message = message;
	}

	public String getUsername() {
		return this.username;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public String getMessage() {
		return this.message;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getRoom() {
		return this.room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

}
