package server.serverDAO;

import java.util.List;
import java.util.Map;

import server.serverDTO.ChatListDTO;
import server.serverDTO.ChatUserDTO;
import server.serverDTO.DepDTO;
import server.serverDTO.EmpDTO;
import server.serverDTO.FileDTO;

public interface ServerDAO {
	
	public boolean accreditation(int id);
	public boolean joinEmployees(EmpDTO emp);
	
	public boolean login(int id, String pw);
	
	public List<EmpDTO> findAllEmployees();
	
	public List<EmpDTO> findEmployees(String name);
	
	public EmpDTO findOneEmployees(int empId);
	
	public boolean modifyEmployees(EmpDTO emp);
	
	public boolean deleteEmployees(int empId);
	
	
	
	public boolean createChat(ChatListDTO chat);
	
	public int findChatSeq();
	
	public ChatListDTO find1v1Chat(int chatId1, int chatId2);
	
	public ChatListDTO findChat(int chatId);
	
	public boolean modifyChatFilePath(int chatId, String filePath);
	
	
	public boolean joinChat(int empId, int chatId);

	public List<ChatUserDTO> findChatUser(Map<String, Object> checkMap);
	
	
	
	

	public boolean saveFile(FileDTO file);
	
	public FileDTO findFile(FileDTO file);



	public DepDTO findDepartments(int depId);
	
	public boolean deleteDepartments(int depId);
	
	public boolean modifyDepartments(DepDTO dep);
	
}
