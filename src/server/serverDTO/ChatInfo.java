package server.serverDTO;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ChatInfo implements Serializable {

    private static final long serialVersionUID = 1L;
	
	private ChatListDTO chatListDTO;
	private List<ChatUserDTO> chatUserDTO;
	
	
	public ChatInfo() {
		// TODO Auto-generated constructor stub
	}


	public ChatInfo(ChatListDTO chatListDTO, List<ChatUserDTO> chatUserDTO) {
		super();
		this.chatListDTO = chatListDTO;
		this.chatUserDTO = chatUserDTO;
	}


	public ChatListDTO getChatListDTO() {
		return chatListDTO;
	}


	public void setChatListDTO(ChatListDTO chatListDTO) {
		this.chatListDTO = chatListDTO;
	}


	public List<ChatUserDTO> getChatUserDTO() {
		return chatUserDTO;
	}


	public void setChatUserDTO(List<ChatUserDTO> chatUserDTO) {
		this.chatUserDTO = chatUserDTO;
	}


	@Override
	public String toString() {
		return "ChatInfo [chatListDTO=" + chatListDTO + ", chatUserDTO=" + chatUserDTO + "]";
	}

	
	
	
	
	
	
	}


