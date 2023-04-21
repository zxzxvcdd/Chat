package server.serverController;

import server.serverDTO.ChatInfo;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerController {

	List<MulThread> threadList = new ArrayList<MulThread>();
	
	Socket socket;

	public ServerController(int portNo1, int portNo2) throws IOException {

		Socket s1 = null;
		
		ServerSocket ss1 = new ServerSocket(portNo1);
		System.out.println("Server Listening port " + portNo1);
		
		while (true) {
			s1 = ss1.accept();
			System.out.println("�����ּ�: " + s1.getInetAddress() + " , ������Ʈ: " + s1.getPort());
			
			MulThread tServer = new MulThread(s1);
			
			tServer.start();

			threadList.add(tServer);
			
			System.out.println("������ �� : " + threadList.size());
			
			
			
		} 
		
		


	
	}
	

}
