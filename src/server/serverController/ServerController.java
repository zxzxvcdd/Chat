package server.serverController;

import server.serverDTO.ChatInfo;
import server.serverDTO.ChatListDTO;
import server.serverDTO.ChatUserDTO;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerController {

	List<MulThread> threadList = new ArrayList<MulThread>();
	static List<ChatThread> chatThreadList = new ArrayList<ChatThread>();
	
	Socket s1;
	static Socket s2;
	
	ServerSocket ss1;
	static ServerSocket ss2;
	
	public ServerController(int portNo1, int portNo2) throws IOException {

		
		ss2 = new ServerSocket(portNo2);
		System.out.println("Server Listening port " + portNo2);
		
		ss1 = new ServerSocket(portNo1);
		System.out.println("Server Listening port " + portNo1);
		
		while (true) {
			s1 = ss1.accept();
			System.out.println("立加林家: " + s1.getInetAddress() + " , 立加器飘: " + s1.getPort());
			
			MulThread tServer = new MulThread(s1);
			
			tServer.start();

			threadList.add(tServer);
			
			System.out.println("立加磊 荐 : " + threadList.size());
			
			
			
		} 
		

	}
	
	
	public static void CreateChatThread() throws IOException {

		
			s2 = ss2.accept();
			System.out.println("立加林家: " + s2.getInetAddress() + " , 立加器飘: " + s2.getPort());
			
			ChatThread tServer = new ChatThread(s2);
			
			tServer.start();

			chatThreadList.add(tServer);

		
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
