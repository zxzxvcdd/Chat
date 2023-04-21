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

	private static List<MulThread> threadList = new ArrayList<MulThread>();

	private Socket s1;

	private ServerSocket ss1;

	public ServerController(int portNo1, int portNo2) throws IOException {

		

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
	
	
	
	
	
	public static void findChatThread(String chat, ChatInfo room,boolean update) throws IOException {

		Set<Integer> userIdList = new HashSet<Integer>();
		ChatUserDTO[] userList = room.getChatUserDTO();
		ChatListDTO chatRoom = room.getChatListDTO();
		int chatId = room.getChatListDTO().getChatId();
		for(ChatUserDTO user : room.getChatUserDTO()) {

			
			userIdList.add(user.getUserId());
		
		}
		
		for (MulThread mulThread :  threadList) {
			
			
			if(userIdList.contains(mulThread.getEmp().getEmployeeId()))
			{
				//update 蔼 true老 矫 room狼 蜡历府胶飘 诀单捞飘
				if(update) {
					
					for(ChatInfo target : mulThread.getRoomList()) {
						
						if(target.getChatListDTO().getChatId()==chatId ) {
							target.setChatListDTO(chatRoom);
							target.setChatUserDTO(userList);
							
						}
						
					}
					
				}
				
				mulThread.sendChat(chat,chatId);
				
			}
				
			


		}
		
		
	}
	

}
