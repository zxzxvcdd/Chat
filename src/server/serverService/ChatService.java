package server.serverService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.serverDAO.ServerDAOImpl;
import server.serverDTO.ChatInfo;
import server.serverDTO.ChatListDTO;
import server.serverDTO.ChatUserDTO;
import server.serverDTO.EmpDTO;

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
	
	public boolean loginEmployee(int id, String pw) {
		
		boolean result = dao.login(id, pw);
		

		
		
		return result;
		
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
				Map<String, Object> checkMap2 = new HashMap<String,Object>();
				ChatListDTO chat = dao.findChat(chatId);

				String type = "chat_id";

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


	
	
	
	
	
}
