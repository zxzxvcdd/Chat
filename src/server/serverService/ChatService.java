package server.serverService;

import java.util.List;

import server.serverDAO.ServerDAOImpl;
import server.serverDBConn.ServerDBConn;
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

	
	
	
	
	
}
