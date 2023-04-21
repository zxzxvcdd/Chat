package server.serverController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import server.serverDTO.ChatInfo;
import server.serverDTO.EmpDTO;
import server.serverService.ChatService;

public class MulThread extends Thread {
	
	ChatService service = new ChatService();
	Socket socket;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	ChatInfo room;
	EmpDTO emp;
	
	 
	
	
	public MulThread(Socket s1){
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
			HashMap<Object, Object> resMap = null;
			
			try {
				resMap = (HashMap<Object, Object>)ois.readObject();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			String command = (String)reqMap.get("command");
			
			
			switch(command) {
			
			case "join":
				
				
				(EmpDTO)reqMap.get("EmpDTO");
		
//				resMap.put("command",);
				
				
				break;
				
			case "login":
				
				
				(EmpDTO)reqMap.get("EmpDTO");
				
				
				
				//로그인 성공시 채팅스레드 생성
				ServerController.CreateChatThread();
				
//				resMap.put("command",);
				
				
				
				break;
			
			case "main":
				
				
				
				
				resMap.put("command","empList");
//				resMap.put("empList",empList);
				
				
				break;
				
			
			}
			
			
//			oos.writeObject(reqMap);
//			oos.flush();
			
			
	
		}
		
		
		
		

	}
	
	

}
