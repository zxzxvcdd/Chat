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

public class MulThread extends Thread {

	ChatService service = new ChatService();
	Socket socket;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	List<ChatInfo> roomList;
	EmpDTO emp;

	public MulThread(Socket s1) {
		try {
			this.socket = s1;

			oos = new ObjectOutputStream(s1.getOutputStream());

			ois = new ObjectInputStream(s1.getInputStream());

		} catch (IOException e) {

		}
	}

	@Override
	public void run() {

		try {
			while (socket.isConnected()) {

				System.out.println(socket.getInetAddress()+" thread running");

				HashMap<Object, Object> reqMap = null;
				HashMap<Object, Object> resMap = new HashMap<Object, Object>();

				try {
					reqMap = (HashMap<Object, Object>) ois.readObject();

				} catch (Exception e) {
//					e.printStackTrace();
				}

				String command = (String) reqMap.get("command");

				System.out.println("req  : " + command);

				/// Ŀ�ǵ尪���� ������ ���񽺸� ����
				switch (command) {

				case "join":

					System.out.println("ũ��" + reqMap.size());
					System.out.println((String) reqMap.get("name"));

					String input = (String) reqMap.get("name");

					resMap.put("command", "afterJoin");
					resMap.put("afterJoin", input);

					oos.writeObject(resMap);
					oos.flush();

//				(EmpDTO)reqMap.get("EmpDTO");

//				resMap.put("command",);

					break;

				case "login":

//				(EmpDTO)reqMap.get("EmpDTO");

//				resMap.put("command",);

					break;

				case "main":

					resMap.put("command", "empList");
//				resMap.put("empList",empList);

					break;

				case "findChat":

//				(EmpDTO)reqMap.get("EmpDTO");

//				resMap.put("command",);

					break;

				case "find1v1":

//				(EmpDTO)reqMap.get("EmpDTO");

//				resMap.put("command",);

					break;

				case "invite":

					break;

				case "send":

					// ä�ù� ��ȣ�� �޾Ƽ� ä�÷��� ã�� ä������ ������Ʈ �� �Ҽӵ� ����鿡�� ����
//				int chatId;
//				String chat = null;
//				ChatInfo targetRoom = null;
//				
//				for(ChatInfo room : roomList){
//					
//					if(room.getChatListDTO().getChatId() == chatId) {
//						targetRoom = room;
//						break;
//					}
//	
//				}
//				
//				try {
//					ServerController.findChatThread(chat, targetRoom,false);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				
//				
					break;

				}

//			oos.writeObject(reqMap);
//			oos.flush();

			}
		} catch (IOException e) {
			// e.printStackTrace();

		} finally {
			// ���� �������� �ε��� ã��
			for (int i = 0; i < ServerController.threadList.size(); i++) {
				if (socket.equals(ServerController.threadList.get(i).socket)) {
					ServerController.threadList.remove(i);

					System.out.println("client ���� ����");
				}
			}
			System.out.println("������ �� : " + ServerController.threadList.size() + " ��");
		} // finally-end

	}

	public EmpDTO getEmp() {
		return emp;
	}

	public void setEmp(EmpDTO emp) {
		this.emp = emp;
	}

	public void sendChat(String chat, int chatId) throws IOException {

		HashMap<Object, Object> resMap = new HashMap<Object, Object>();

		String resCommand = "sendchat";

		resMap.put("command", resCommand);
		resMap.put("chatString", chat);
		resMap.put("chatId", chatId);
		oos.writeObject(resMap);
		oos.flush();

	}

	public void updateRoom() throws IOException {

		HashMap<Object, Object> resMap = new HashMap<Object, Object>();

		String resCommand = "updateRoom";

		resMap.put("command", resCommand);
		resMap.put("roomList", roomList);
		oos.writeObject(resMap);
		oos.flush();

	}

	public List<ChatInfo> getRoomList() {
		return roomList;
	}

}
