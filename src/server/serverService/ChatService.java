package server.serverService;

import java.util.List;

import server.serverDAO.ServerDAOImpl;
import server.serverDTO.EmpDTO;

public class ChatService {

	
	ServerDAOImpl dao = new ServerDAOImpl();
	
	

	public List<EmpDTO> selectEmpByName(String name) {

		List<EmpDTO> empList = dao.findEmployees(name);
		
	
		return empList;
		
	}

	
	
	
	
	
}
