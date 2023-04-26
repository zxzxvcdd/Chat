package server.serverService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.serverDAO.ServerDAOImpl;
import server.serverDTO.ChatInfo;
import server.serverDTO.ChatListDTO;
import server.serverDTO.ChatUserDTO;
import server.serverDTO.EmpDTO;
import server.serverDTO.FileDTO;

public class ChatService {

	
	ServerDAOImpl dao = ServerDAOImpl.getInstance();
	
	private static ChatService instance = new ChatService();
	
	
	private ChatService() {

	}
	
    public static ChatService getInstance() {
        return instance;
    }


	
	public boolean joinEmployee(EmpDTO emp) {
		
		System.out.println("service.joinEmployee");
		
		boolean result = dao.joinEmployees(emp);

		System.out.println(result);
		
		return result;
		  
		  
	}
	
	public EmpDTO loginEmployee(int id, String pw) {
		
		boolean result = dao.login(id, pw);
		
		if(result) {
			
			EmpDTO emp =dao.findOneEmployees(id);
			
			return emp;
			
		}else {
			
			return null;
		}
			

		
		

		
	}
	
	public boolean updateEmployee(EmpDTO emp) {
		
		boolean result = dao.modifyEmployees(emp);
		
		return result;
	}
	
	public boolean accreditationEmployee(int id) {
		
		boolean result = dao.accreditation(id);
		
		return result;
		
	}
	
	
	

	public List<EmpDTO> selectEmpByName(String name) {

		List<EmpDTO> empList = dao.findEmployees(name);
		
	
		return empList;
		
	}
	
	

	public List<ChatInfo> findChat(Map<String, Object> checkMap) {

		List<ChatUserDTO> userList = null; // 

		List<ChatInfo> roomList = new ArrayList<ChatInfo>();

		
		userList = dao.findChatUser(checkMap);

		System.out.println(userList);
		if (userList != null) {
			for (ChatUserDTO user : userList) {

				int chatId = user.getChatId();
				
				ChatListDTO chat = dao.findChat(chatId);

				String type = "chat_id";
				Map<String, Object> checkMap2 = new HashMap<String,Object>();
				checkMap2.put("type", type);
				checkMap2.put("value", chatId);

				List<ChatUserDTO> userList2 = dao.findChatUser(checkMap2); 

				ChatInfo chatInfo = new ChatInfo(chat, userList2);

				roomList.add(chatInfo);

			}
		}

		return roomList;

	}

	public List<EmpDTO> findAllEmployees() {

		return dao.findAllEmployees();

	}

	

	// dao ��ü�� multhread�� �Ѱ��ִ� �޼ҵ�
	public List<FileDTO> findFile(int chatId) {
		List<FileDTO> files = dao.findFileList(chatId);
		if (files == null) System.out.println("chatservice");
		return files;		
	}
	
	// Gui���� ������ DB�� ����
	public boolean SaveFile(int employee_id, int chat_id, String fileName) throws Exception {
		FileDTO fdto = new FileDTO();
		
		fdto.setFileName(fileName);
		fdto.setChatId(chat_id);
		fdto.setEmployeeId(employee_id);
		fdto.setFilePath("C:/file/"  + chat_id + "/" + employee_id + "/" +fileName + ".txt");
		boolean saveFile = dao.saveFile(fdto);
		
		return saveFile; // file ���� ����, ����Ȯ�ΰ���
	}

	// server pc�� �ִ� ���ϳ����� �о�ͼ� �����ش�
	public String readFile(FileDTO fdto) throws IOException {
		File file = new File(fdto.getFilePath());
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		String content = "";
		
		while((line = br.readLine()) != null)	content += (line +"\n");
		br.close();
		return content;
	}
	
	public boolean writeFile (int employeeId, int chatId, String fileName, String content) throws IOException {
		String path = "C:/file/"  + chatId + "/" + employeeId;
		PrintWriter pw = null;
		boolean writeFilePath = true;
		try {
			if (path != null) {
				File file = new File(path);
				if(!file.exists()) file.mkdirs();
				File file2 = new File(file, fileName + ".txt");
				if(!file2.exists())	file2.createNewFile();
				FileWriter fw = new FileWriter(file2);
				pw = new PrintWriter(fw, true);
				pw.print(content);
				pw.close();
			}
			else  writeFilePath = false;
		} finally {}
		return writeFilePath;
	}


	
	
	   public String readChat(ChatInfo room) throws IOException {
		      // TODO Auto-generated method stub
		      
		      String chatPath = room.getChatListDTO().getChatPath();
		      
		      File file = new File("chatPath");
		      FileReader fr = new FileReader(file);
		      BufferedReader br = new BufferedReader(fr);
		      String line = "";
		      String chatString = "";
		      
		      while((line = br.readLine()) != null)   chatString += (line +"\n");
		      br.close();
		      return chatString;
		      

		      
		   }
	   
	   
		public boolean writeChat(ChatInfo room, String chat) throws Exception {
			
			String path = room.getChatListDTO().getChatPath();
		
			
			boolean result = false;
			
			try(FileWriter fw = new FileWriter(path, true)) {
					fw.write(chat);
					fw.close();
					result = true;
			}catch(Exception e) {
				result = false;
			}finally {
			
			return result;
			}
			
		}

		public ChatInfo inviteEmployees(ChatInfo updateRoom, EmpDTO newEmp) {
			// TODO Auto-generated method stub
			
			int empId = newEmp.getEmployeeId();
			
			int chatId = updateRoom.getChatListDTO().getChatId();
			
			
			boolean result = dao.joinChat(empId, newEmp.getName(), chatId);
			
			if(result) {
				
				Map<String, Object> checkMap = new HashMap<String,Object>();
				checkMap.put("type", "chat_id");
				checkMap.put("value", chatId);
				
				List<ChatUserDTO> userList = dao.findChatUser(checkMap);				
				
				updateRoom.setChatUserDTO(userList);
			
				return updateRoom;
				
				
			}else {
				
				return null;
			}
			
			
			
			
		}

		public ChatListDTO createRoom(String chatName) {
			// TODO Auto-generated method stub
			
			ChatListDTO chat = new ChatListDTO();
			
			String path = "";
			
			chat.setChatName(chatName);
			
			
			
			boolean CreateChat = dao.createChat(chat);
			
			if(CreateChat) {
				
				int chatId =dao.findChatSeq();
				
				chat.setChatId(chatId);
				
				
				path = "C:/chat/"  + chatId;
				PrintWriter pw = null;
				boolean createFile = true;
				try {
					if (path != null) {
						File file = new File(path);
						if(!file.exists()) file.mkdirs();
						File file2 = new File(file, chatName + ".txt");
						if(!file2.exists())	file2.createNewFile();
					}
					else  createFile = false;
				}catch(Exception e) {
					createFile = false;
				}
				
				
				chat.setChatPath(path);
				
				
				boolean updateResult = dao.updateChat(chat);
				if(updateResult) {
					return chat;
				}
			}
			
			
			return null;
		}
	   
	
	
	
	
}
