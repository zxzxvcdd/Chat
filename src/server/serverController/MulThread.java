package server.serverController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import server.serverDTO.ChatInfo;
import server.serverDTO.ChatListDTO;
import server.serverDTO.ChatUserDTO;
import server.serverDTO.EmpDTO;
import server.serverDTO.FileDTO;
import server.serverService.ChatService;

public class MulThread extends Thread {

	ChatService service = ChatService.getInstance();
	Socket socket;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	List<ChatInfo> roomList;
	EmpDTO emp;
	List<EmpDTO> empList = null;
	HashMap<Object, Object> reqMap;
	HashMap<Object, Object> resMap;

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

				reqMap = null;
				resMap = new HashMap<Object, Object>();

				try {
					reqMap = (HashMap<Object, Object>) ois.readObject();

				} catch (Exception e) {
//					e.printStackTrace();
				}

				String command = (String) reqMap.get("command");

				System.out.println("req  : " + command);

				/// 커맨드값으로 수행할 서비스를 선택
				switch (command) {

				case "join":

					emp = (EmpDTO) reqMap.get("emp");

					System.out.println(emp);
					boolean joinResult = service.joinEmployee(emp);

					System.out.println(joinResult);

					if (joinResult) {
						resMap.put("command", "afterJoin");
						resMap.put("joinResult", joinResult);
						System.out.println("성공");

					} else {
						resMap.put("command", "afterJoin");
						resMap.put("joinResult", joinResult);
						System.out.println("실패");
					}

					break;
					
			case "accreditation":
					
					emp = (EmpDTO)reqMap.get("emp");
					System.out.println("accre:"+emp);
					boolean accResult = service.accreditationEmployee(emp.getEmployeeId()); 
					System.out.println("Mul1:"+emp.getEmployeeId());
					System.out.println("Mul2:accreditationresult:"+accResult);
					
					
						resMap.put("command", "afterAccreditation");
						resMap.put("accResult", accResult);
					
					System.out.println(resMap);
					break;
					
				case "update":
					
					emp = (EmpDTO)reqMap.get("emp");
					System.out.println("update: "+emp);
					boolean Updateresult = service.updateEmployee(emp);
					System.out.println("Updateresult: "+Updateresult);
					
					resMap.put("command", "afterUpdate");
					resMap.put("Updateresult", Updateresult);
					
					break;

				case "login":

					System.out.println(reqMap.containsKey("emp"));
					emp = (EmpDTO) reqMap.get("emp");
					System.out.println(emp);
					this.emp = service.loginEmployee(emp.getEmployeeId(), emp.getPw());

					resMap.put("emp", emp);

					if (emp != null) {
						resMap.put("command", "afterLogin");
						resMap.put("loginResult", true);

					} else {
						resMap.put("command", "afterLogin");
						resMap.put("loginResult", false);

					}

					break;

				case "selectByName":

					String name = (String) reqMap.get("name");
					empList = service.selectEmpByName(name);

					resMap.put("command", "afterSelectByName");
					resMap.put("empList", empList);

					System.out.println(empList);

					break;

				case "main":

					String type = (String) reqMap.get("type");

					Map<String, Object> checkMap = new HashMap<String, Object>();

					checkMap.put("type", type);
					checkMap.put("value", reqMap.get("empId"));

					System.out.println(checkMap);

					roomList = service.findChat(checkMap);

					System.out.println("메인"+roomList);
					
					empList = service.findAllEmployees();

					resMap.put("command", "afterMain");
					resMap.put("roomList", roomList);
					resMap.put("empList", empList);
					
					String online ="";
					if(ServerController.threadList.size()>0) {
					for(MulThread th : ServerController.threadList) {
						
						online += th.getEmp().getName() + " "+ th.getEmp().getJobTitle()+"\n";
						
					}
					}

					resMap.put("online", online);
					
					break;

				case "findChat":

//				(EmpDTO)reqMap.get("EmpDTO");

//				resMap.put("command",);

					break;

				case "find1v1":

//				(EmpDTO)reqMap.get("EmpDTO");

//				resMap.put("command",);

					break;

				case "findFileList": // service로 부터 filelist 객체를 받아 Rcv로 보냄
					int fchatId = (Integer)reqMap.get("chatId");
					List<FileDTO> fileList = service.findFile(fchatId);
					resMap.put("command", "afterFindFileList"); 
					resMap.put("fileList", fileList);
					
					System.out.println(fileList);
					break;

				case "downFile": // service에 해당 파일을 찾아서 파일 내용을 읽와서 Rcv로 보냄
					FileDTO fdto = new FileDTO(); 
					fdto = (FileDTO)reqMap.get("fdto");
					String dcontent = service.readFile(fdto);
					int op = (Integer)reqMap.get("reFile");
					resMap.put("command", "downContent");
					resMap.put("content", dcontent);
					resMap.put("reFile", op);
					break;
					

				case "saveFile":
					String fileName = (String) reqMap.get("fileName");
					int schatId = (Integer) reqMap.get("chatId");
					int employeeId = (Integer) reqMap.get("employeeId");
					String scontent = (String) reqMap.get("content");
					Boolean writeFilePath;
					resMap.put("command", "afterSaveFile");
					try {
						boolean saveFile = service.SaveFile(employeeId, schatId, fileName);
						if(!saveFile) {
							boolean alarm = true;
							reqMap.put("alarm", alarm);
						}
						else {
							service.writeFile(employeeId, schatId, fileName, scontent);
							boolean alarm = false;
	
							
							
						}
					} catch (Exception e) {}					
					break;
					
					

				case "readChat":

					ChatInfo readRoom = (ChatInfo) reqMap.get("room");

					String chat = service.readChat(readRoom);

					resMap.put("command", "afterReadChat");

					resMap.put("chatId", readRoom.getChatListDTO().getChatId());

					resMap.put("chat", chat);

					break;

				case "sendChat":

					
					ChatInfo sendRoom = (ChatInfo) reqMap.get("room");

					String sendString = (String) reqMap.get("chat");

					System.out.println(reqMap);
					
					String newChat = emp.getName() + ": " + sendString + "\n";

					boolean sendResult = service.writeChat(sendRoom, newChat);

		
					
					
					resMap.put("chatId", sendRoom.getChatListDTO().getChatId());

					if (sendResult) {

						
						String sendChat = service.readChat(sendRoom);

						resMap.put("chat", sendChat);

						List<MulThread> roomThreads = findChatThread(sendRoom, false);

						
						System.out.println(roomThreads);
						
						for (MulThread roomThread : roomThreads) {

							roomThread.sendChat(resMap);

						}

					}

					resMap.put("command", "afterSendChat");
					resMap.put("result", sendResult);

					break;

				case "invite":

					ChatInfo updateRoom = (ChatInfo) reqMap.get("room");
					EmpDTO newEmp = (EmpDTO) reqMap.get("newEMp");

					boolean checkNew = (boolean) reqMap.get("newRoom");

					if (checkNew || updateRoom == null) {

						System.out.println("invite " + reqMap);

						String chatName = (String) reqMap.get("chatName");

						List<EmpDTO> newUsers = (List<EmpDTO>) reqMap.get("newUsers");

						
						

						if (chatName == null) {

							chatName = "";
							

							for (EmpDTO newUser : newUsers) {

								chatName += newUser.getName() + "님 ";

							}
							chatName += "의 채팅방";
						}

					

						ChatListDTO newRoom = service.createRoom(chatName);

						updateRoom = new ChatInfo();

						updateRoom.setChatListDTO(newRoom);
						int cnt = 0;
						String failUsers = "";
						
						invite(updateRoom, emp);
						for (EmpDTO newUser : newUsers) {

							resMap = invite(updateRoom, newUser);

							int result = (Integer) resMap.get("inviteResult");

							if (result != 1) {
								cnt++;
								failUsers += newUser.getName() + "\tfail result:" + result + "\n";
							}

						}
						updateRoom(updateRoom.getChatListDTO().getChatId());

				
						
						resMap = new HashMap<Object, Object>();
						resMap.put("chatId", updateRoom.getChatListDTO().getChatId());
						resMap.put("command", "afterCreateRoom");
						resMap.put("CreateResult", "failUsers");
						resMap.put("room", updateRoom);

					} else {

						
						resMap = invite(updateRoom, newEmp);

					}

					break;

				default:
					break;

				}

				oos.writeObject(resMap);
				oos.flush();

			}
		} catch (

		Exception e) {
			e.printStackTrace();

		} finally {
			// 나간 쓰레드의 인덱스 찾기
			for (int i = 0; i < ServerController.threadList.size(); i++) {
				if (socket.equals(ServerController.threadList.get(i).socket)) {
					ServerController.threadList.remove(i);

					System.out.println("client 연결 해제");
				}
			}
			System.out.println("접속자 수 : " + ServerController.threadList.size() + " 명");
		} // finally-end

	}

	public EmpDTO getEmp() {
		return emp;
	}

	public List<ChatInfo> getRoomList() {
		return roomList;
	}

	public void sendChat(HashMap<Object, Object> resMap) throws IOException {

	
		if(reqMap==null||!(reqMap.containsKey("newRoom"))) {
		resMap.put("command", "send");
		oos.writeObject(resMap);
		oos.flush();
		}

	}

	public HashMap<Object, Object> invite(ChatInfo updateRoom, EmpDTO newEmp) throws Exception {

		updateRoom = service.inviteEmployees(updateRoom, newEmp);
	
		HashMap<Object, Object> resMap = new HashMap<Object, Object>();

		resMap.put("chatId", updateRoom.getChatListDTO().getChatId());

		
		if (updateRoom != null) {

			//방정보 업데이트 후 쓰레드 반환
			System.out.println("findChatThread");
			List<MulThread> roomThreads = findChatThread(updateRoom, true);

			boolean updateResult;
			if(emp.getName().equals(newEmp.getName())) {
			
				updateResult=true;
				
			}else {
			String updateString = emp.getName() + " " + emp.getJobTitle() + "님이 " + newEmp.getName() + " "
					+ newEmp.getJobTitle() + "님을 초대하였습니다.\n";


			updateResult = service.writeChat(updateRoom, updateString);

			}
			
			if (updateResult) {

				String sendChat = service.readChat(updateRoom);

				resMap.put("chat", sendChat);

				
				for (MulThread roomThread : roomThreads) {

					roomThread.sendChat(resMap);

				}
				

				resMap.remove("chat");

				resMap.put("inviteResult", 1);
				// 전부 성공
			} else {

				resMap.put("inviteResult", 2);
				// 초대 완료 chat write 실패

			}

		}else {

			resMap.put("inviteResult", 0);
			// 초대 실패

		}

		resMap.put("command", "afterInvite");

		return resMap;

	}

	public void updateRoom(int chatId) throws IOException {

		HashMap<Object, Object> resMap = new HashMap<Object, Object>();

		String resCommand = "updateRoom";

		resMap.put("command", resCommand);
		resMap.put("chatId", chatId);
		resMap.put("roomList", roomList);
		oos.writeObject(resMap);
		oos.flush();

	}

	public List<MulThread> findChatThread(ChatInfo room, boolean update) throws IOException {

		
		System.out.println(room);
		
		List<MulThread> roomThreads = new ArrayList<MulThread>();

		Set<Integer> userIdList = new HashSet<Integer>();
		List<ChatUserDTO> userList = room.getChatUserDTO();
		ChatListDTO chatRoom = room.getChatListDTO();
		int chatId = room.getChatListDTO().getChatId();
		for (ChatUserDTO user : room.getChatUserDTO()) {

			userIdList.add(user.getEmployeeId());

		}
		
		
		for (MulThread mulThread : ServerController.threadList) {

		
			if (userIdList.contains(mulThread.getEmp().getEmployeeId())) {
				// update 값 true일 시 room의 유저리스트 업데이트
				if (update) {

					for (ChatInfo target : mulThread.getRoomList()) {

						if (target.getChatListDTO().getChatId() == chatId) {
							target.setChatListDTO(chatRoom);
							target.setChatUserDTO(userList);

							break;

						}

					}

					System.out.println("업데이트");
					mulThread.updateRoom(chatId);

				}

				roomThreads.add(mulThread);

			}

		}

		return roomThreads;

	}

}
