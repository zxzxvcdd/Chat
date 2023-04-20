package test.server.serverDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import server.serverDAO.ServerDAO;
import server.serverDAO.ServerDAOImpl;
import server.serverDTO.EmpDTO;

class ServerDAOImplTest{

	ServerDAO dao = new ServerDAOImpl();
	
	
	@Test
	void testServerDAOImpl() {
		
		fail("Not yet implemented");
	}

	@Test
	void testJoinEmployees() {
		
		
		EmpDTO emp = new EmpDTO(110111,"000001","È«±æµ¿",01,"010-1111-1111","admin","´ë¸®"); 
		
		boolean result = dao.joinEmployees(emp);
		
		assertEquals(true, result);
		
		fail("Not yet implemented");
	}

	@Test
	void testFindAllEmployees() {
		fail("Not yet implemented");
	}

	@Test
	void testFindOneEmployees() {
		fail("Not yet implemented");
	}

	@Test
	void testModifyEmployees() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteEmployees() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateChat() {
		fail("Not yet implemented");
	}

	@Test
	void testFindChatSeq() {
		fail("Not yet implemented");
	}

	@Test
	void testFindChat() {
		fail("Not yet implemented");
	}

	@Test
	void testModifyChatFilePath() {
		fail("Not yet implemented");
	}

	@Test
	void testJoinChat() {
		fail("Not yet implemented");
	}

	@Test
	void testFindChatUser() {
		fail("Not yet implemented");
	}

	@Test
	void testSaveFile() {
		fail("Not yet implemented");
	}

	@Test
	void testFindFile() {
		fail("Not yet implemented");
	}

	@Test
	void testFindDepartments() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteDepartments() {
		fail("Not yet implemented");
	}

	@Test
	void testModifyDepartments() {
		fail("Not yet implemented");
	}

}
