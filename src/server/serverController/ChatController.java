package server.serverController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import server.serverDTO.ChatInfo;
import server.serverDTO.ChatListDTO;
import server.serverDTO.ChatUserDTO;

public class ChatController {
	
	
	static List<ChatThread> chatThreadList = new ArrayList<ChatThread>();
	Socket socket;
	
	public ChatController(int portNo1, int portNo2) throws IOException {

	
		Socket s1 = null;
		
		ServerSocket ss1 = new ServerSocket(portNo2);
		System.out.println("Server Listening port " + portNo2);
		
		
		while (true) {
			s1 = ss1.accept();
			System.out.println("立加林家: " + s1.getInetAddress() + " , 立加器飘: " + s1.getPort());
			
			ChatThread tServer = new ChatThread(s1);
			
			tServer.start();

			chatThreadList.add(tServer);

		}
		
	}
	
	public static void findChatThread(String chat, ChatInfo room,boolean update) throws IOException {

		Set<Integer> userIdList = new HashSet<Integer>();
		ChatUserDTO[] userList = room.getChatUserDTO();
		ChatListDTO chatRoom = room.getChatListDTO();
		int chatId = room.getChatListDTO().getChatId();
		for(ChatUserDTO user : room.getChatUserDTO()) {

			
			userIdList.add(user.getUserId());
		
		}
		
		for (ChatThread chatThread : chatThreadList) {
			
			
			if(userIdList.contains(chatThread.getEmp().getEmployeeId()))
			{
				//update 蔼 true老 矫 room狼 蜡历府胶飘 诀单捞飘
				if(update) {
					
					for(ChatInfo target : chatThread.getRoomList()) {
						
						if(target.getChatListDTO().getChatId()==chatId ) {
							target.setChatListDTO(chatRoom);
							target.setChatUserDTO(userList);
							
						}
						
					}
					
				}
				
				chatThread.sendChat(chat,chatId);
				
			}
				
			


		}
		
		
	}
}
	
	
	
		


