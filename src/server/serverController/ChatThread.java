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
			
				//emp_id로 소속된 채팅방 목록 생성후 넘김
				
//				roomList = service.xx;
				
				break;
			
			
			case "findChat":
				
				
				(EmpDTO)reqMap.get("EmpDTO");
		
//				resMap.put("command",);
				
				
				break;
				
			case "find1v1":
				
				
				(EmpDTO)reqMap.get("EmpDTO");
				
				
//				resMap.put("command",);
				
				
				break;
				
			case "invite":
				
				
				break;
			
			case "send":
				
				//채팅방 번호를 받아서 채팅룸을 찾고 채팅파일 업데이트 후 소속된 사원들에게 전송
				int chatId;
				String chat;
				ChatInfo targetRoom = null;
				
				for(ChatInfo room : roomList){
					
					if(room.getChatListDTO().getChatId() == chatId) {
						targetRoom = room;
						break;
					}
	
				}
				
				ServerController.findChatThread(chat, targetRoom,false);
				
				
				
				break;
				
			
			}
			
			
//			oos.writeObject(reqMap);
//			oos.flush();
			
			
	
		}
		
		
		
		

	}

	public EmpDTO getEmp() {
		return emp;
	}

	public void setEmp(EmpDTO emp) {
		this.emp = emp;
	}


	public void sendChat(String chat, int chatId) throws IOException {
		
		HashMap<Object, Object> resMap = new HashMap<Object, Object>();
		
		String resCommand = "chat";
		
		resMap.put("command",resCommand);
		resMap.put("chatString",chat);
		resMap.put("chatId", chatId);
		oos.writeObject(resMap);
		oos.flush();
		
		
	}
	
	public void updateRoom() throws IOException {
		
		HashMap<Object, Object> resMap = new HashMap<Object, Object>();
		
		String resCommand = "updateRoom";
		
		resMap.put("command",resCommand);
		resMap.put("roomList",roomList);
		oos.writeObject(resMap);
		oos.flush();
	
	}

	public List<ChatInfo> getRoomList() {
		return roomList;
	}


	

	
	
	
}
