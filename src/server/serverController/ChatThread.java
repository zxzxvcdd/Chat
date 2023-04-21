package server.serverController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import server.serverDTO.ChatInfo;
import server.serverDTO.EmpDTO;
import server.serverService.ChatService;

public class ChatThread extends Thread {

	ChatService service = new ChatService();
	Socket socket;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	List<ChatInfo> roomList;
	EmpDTO emp;
	 
	
	
	
	public ChatThread(Socket s1){
		try {
		this.socket = s1;

		oos = new ObjectOutputStream(s1.getOutputStream());
		
		ois = new ObjectInputStream(s1.getInputStream());
		
		}catch(IOException e){
			
			
		}
	}
	
	@Override
	public void run() {
		
		
		
		while(socket.isConnected()) {
			
			HashMap<Object, Object> reqMap = null;
			HashMap<Object, Object> resMap = new HashMap<Object, Object>();
			
			try {
				reqMap = (HashMap<Object, Object>)ois.readObject();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			String command = (String)reqMap.get("command");
			
			
			switch(command) {
			
			case "login":
			
				//emp_id肺 家加等 盲泼规 格废 积己饶 逞辫
				
//				roomList = service.xx;
				
				break;
			
			


				
			
			}
			
			
//			oos.writeObject(reqMap);
//			oos.flush();
			
			
	
		}
		
		
		
		

	}




	

	
	
	
}
