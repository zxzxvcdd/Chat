package server.serverDTO;

import java.io.Serializable;
import java.util.Arrays;

public class ChatInfo implements Serializable {
    // serialVersionUID�� ����ϴ� �ڹ� �ý��۰� �����ؾ� �մϴ�.
    private static final long serialVersionUID = 1L;
	
	private ChatListDTO chatListDTO;
	private ChatUserDTO[] chatUserDTO;
	
	
	public ChatInfo() {
		// TODO Auto-generated constructor stub
	}

	
	

	public ChatInfo(ChatListDTO chatListDTO, ChatUserDTO[] chatUserDTO) {
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


	public ChatUserDTO[] getChatUserDTO() {
		return chatUserDTO;
	}


	public void setChatUserDTO(ChatUserDTO[] chatUserDTO) {
		this.chatUserDTO = chatUserDTO;
	}




	@Override
	public String toString() {
		return "ChatInfo [chatListDTO=" + chatListDTO + ", chatUserDTO=" + Arrays.toString(chatUserDTO) + "]";
	}
	
	
	
	
	
	}


