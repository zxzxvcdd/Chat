package server.serverDTO;

import java.io.Serializable;

public class ChatListDTO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private int chatId;
	private String chatName;
	private String chatPath;
	
	
	public ChatListDTO() {
		// TODO Auto-generated constructor stub
	}


	public ChatListDTO(int chatId, String chatName, String chatPath) {
		super();
		this.chatId = chatId;
		this.chatName = chatName;
		this.chatPath = chatPath;
	}


	public int getChatId() {
		return chatId;
	}


	public void setChatId(int chatId) {
		this.chatId = chatId;
	}


	public String getChatName() {
		return chatName;
	}


	public void setChatName(String chatName) {
		this.chatName = chatName;
	}


	public String getChatPath() {
		return chatPath;
	}


	public void setChatPath(String chatPath) {
		this.chatPath = chatPath;
	}


	@Override
	public String toString() {
		return "ChatListDTO [chatId=" + chatId + ", chatName=" + chatName + ", chatPath=" + chatPath + "]";
	}
	
	
	
	
	
	
	
	
	
	
}
