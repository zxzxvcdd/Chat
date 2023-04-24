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

	static List<MulThread> threadList = new ArrayList<MulThread>();

	private Socket s1;

	private ServerSocket ss1;

	public ServerController(int portNo) throws IOException {

		

		ss1 = new ServerSocket(portNo);
		System.out.println("Server Listening port " + portNo);
		
		while (true) {
			s1 = ss1.accept();
			System.out.println("立加林家: " + s1.getInetAddress() + " , 立加器飘: " + s1.getPort());
			
			MulThread tServer = new MulThread(s1);
			
			tServer.start();

			threadList.add(tServer);
			
			System.out.println("立加磊 荐 : " + threadList.size());
			
			
			
		} 
		

	}
	
	
	
	
}
