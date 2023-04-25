package server.serverDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import server.serverDBConn.ServerDBConn;
import server.serverDTO.ChatListDTO;
import server.serverDTO.ChatUserDTO;
import server.serverDTO.DepDTO;
import server.serverDTO.EmpDTO;
import server.serverDTO.FileDTO;

public class ServerDAOImpl implements ServerDAO {

	
	private static ServerDAOImpl instance = new ServerDAOImpl();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private ServerDAOImpl() {

		try {
			con = new ServerDBConn().getCon();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
    public static ServerDAOImpl getInstance() {
        return instance;
    }

	@Override
	public boolean joinEmployees(EmpDTO emp) {

		String sql = "insert into employees(employee_id, pw, name, department_id, tel, job_title)"
				+ " values(?, ?, ?, ?, ?, ?)";


		if (emp != null) {
			try {

			
				
				pst = con.prepareStatement(sql);
				pst.setQueryTimeout(3);
				
				pst.setInt(1, emp.getEmployeeId());
				pst.setString(2, emp.getPw());
				pst.setString(3, emp.getName());
				pst.setInt(4, emp.getDepartmentId());
				pst.setString(5, emp.getTel());
				pst.setString(6, emp.getJobTitle());

				int result = pst.executeUpdate();
				System.out.println("dao result : " + result);

				if (result >= 1) {
					return true;
				} else {
					return false;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				System.out.println("joinEmployees error");
				return false;
			}
		}
		
		return false;

	}

	@Override
	public boolean login(int id, String pw) { // 로그인

		String sql = "select employee_id, pw from employees where employee_id = ? and pw = ?";
		int Id;
		String Pw;
		boolean a = true;

		try {
			pst = con.prepareStatement(sql);

			pst.setInt(1, id);
			pst.setString(2, pw);

			rs = pst.executeQuery();

			rs.next();
			Id = rs.getInt("employee_id");
			Pw = rs.getString("pw");

			if ((Id == id) && Pw.equals(pw)) {
				a = true;
			} else {
				a = false;
			}

			if (a) {
				return true;
			} else {
				return false;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public List<EmpDTO> findAllEmployees() {
		// TODO Auto-generated method stub
		String sql = "select e.*, d.department_name" + " from employees e, departments d"
				+ " where e.department_id = d.department_id";

		try {
			pst = new LoggableStatement(con, sql);

//
//			System.out.println("\t sQuery ? " +
//                    ((LoggableStatement)pst).getQueryString() + "\n");
//
//			
			rs = pst.executeQuery();

			List<EmpDTO> empList = new ArrayList<EmpDTO>();

			
			
			while (rs.next()) {

				EmpDTO emp = new EmpDTO(rs.getInt("employee_id"), rs.getString("pw"), rs.getString("name"),
						rs.getInt("department_id"), rs.getString("tel"), rs.getString("admin"),
						rs.getString("job_title"));

				emp.setDepartmentName(rs.getString("department_name"));
				empList.add(emp);

			}

			return empList;

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("findAllEmployees error");

			return null;

		}

	}

	@Override
	public EmpDTO findOneEmployees(int empId) {
		// TODO Auto-generated method stub
		String sql = "select * from employees" + " where employee_id = ?";

		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			rs.next();
			EmpDTO emp = new EmpDTO(rs.getInt("employee_id"), rs.getString("pw"), rs.getString("name"),
					rs.getInt("department_id"), rs.getString("tel"), rs.getString("admin"), rs.getString("job_title"));

			return emp;

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("findOneEmployees error");

			return null;

		}

	}

	@Override
	public boolean modifyEmployees(EmpDTO emp) {
		// TODO Auto-generated method stub

		String sql = "update employees" + " set (pw, name, deparment_id, tel, job_title)"
				+ " = (?, ?, ?, ?, ?) where employee_id = ?";

		try {
			pst = con.prepareStatement(sql);

			pst.setString(1, emp.getPw());
			pst.setString(2, emp.getName());
			pst.setInt(3, emp.getDepartmentId());
			pst.setString(4, emp.getTel());
			pst.setString(5, emp.getJobTitle());
			pst.setInt(6, emp.getEmployeeId());

			int result = pst.executeUpdate();

			if (result >= 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("modifyEmployees error");

			return false;

		}

	}

	@Override
	public boolean deleteEmployees(int empId) {
		// TODO Auto-generated method stub

		String sql = "delete employees" + " where employee_id = ?";

		try {
			pst = con.prepareStatement(sql);

			pst.setInt(1, empId);

			int result = pst.executeUpdate();

			if (result >= 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("deleteEmployees error");

			return false;

		}

	}

	@Override
	public boolean createChat(ChatListDTO chat) {
		// TODO Auto-generated method stub

		String sql = "insert into chat_lists (chat_id, chat_name, chat_path)" + " values(chat_seq.NEXTVAL, ?, ?)";

		try {
			pst = con.prepareStatement(sql);

			pst.setString(1, chat.getChatName());
			pst.setString(2, chat.getChatPath());

			int result = pst.executeUpdate();

			if (result >= 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("createChat error");

			return false;

		}

	}

	@Override
	public int findChatSeq() {
		// TODO Auto-generated method stub

		String sql = "select chat_list_seq.CURRVAL from dual";

		try {
			pst = con.prepareStatement(sql);

			rs = pst.executeQuery();

			rs.next();

			int result = rs.getInt(1);

			return result;

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("findChatSeq error");

			return 0;

		}

	}

	@Override
	public List<EmpDTO> findEmployees(String name) {

		String sql = "select * from employees" + " where name = ?";

		try {

			pst = con.prepareStatement(sql);

			pst.setString(1, name);

			rs = pst.executeQuery();

			List<EmpDTO> empList = new ArrayList<EmpDTO>();

			while (rs.next()) {

				EmpDTO emp = new EmpDTO(rs.getInt("employee_id"), rs.getString("pw"), rs.getString("name"),
						rs.getInt("department_id"), rs.getString("tel"), rs.getString("admin"),
						rs.getString("job_title"));

				empList.add(emp);

			}

			return empList;

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("findEmployees error");

			return null;

		}

	}

	@Override
	public boolean accreditation(int id) { // 본인인증

		String sql = "select employee_id from employees where employee_id = ?";
		int Id;
		boolean a = true;

		try {
			pst = con.prepareStatement(sql);

			pst.setInt(1, id);

			rs = pst.executeQuery();

			while (rs.next()) {
				Id = rs.getInt("employee_id");

				if ((Id == id)) {
					a = true;
				} else {
					a = false;
				}
			}

			if (a) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public ChatListDTO find1v1Chat(int chatId1, int chatId2) {
		// TODO Auto-generated method stub

		String sql = "select * from chat_lists" + " where chat_id = "
				+ " (select chat_id from chat_users a, (select * from chat_users where employee_id = ?) b "
				+ " where a.employee_id =?" + " and and a.chat_id=b.chat_id)" + " and chat_id="
				+ " (select chat_id from chat_users group by chat_id having count(*) = 2;)";

		try {
			pst = con.prepareStatement(sql);

			pst.setInt(1, chatId1);
			pst.setInt(2, chatId2);
			rs = pst.executeQuery();
			rs.next();
			ChatListDTO chat = new ChatListDTO(rs.getInt("chat_id"), rs.getString("chat_name"),
					rs.getString("chat_path"));

			return chat;

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("ChatListDTO error");

			return null;

		}

	}

	@Override
	public ChatListDTO findChat(int chatId) {
		// TODO Auto-generated method stub

		String sql = "selcet * from chat_lists where chat_id = ?";

		try {
			pst = con.prepareStatement(sql);

			pst.setInt(1, chatId);

			rs = pst.executeQuery();
			rs.next();
			ChatListDTO chat = new ChatListDTO(rs.getInt("chat_id"), rs.getString("chat_name"),
					rs.getString("chat_path"));

			return chat;

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("ChatListDTO error");

			return null;

		}

	}

	@Override
	public boolean modifyChatFilePath(int chatId, String filePath) {
		// TODO Auto-generated method stub

		String sql = "update chat_lists set chat_path = ? where chat_id = ?";

		try {
			pst = con.prepareStatement(sql);

			pst.setString(1, filePath);
			pst.setInt(2, chatId);

			int result = pst.executeUpdate();

			if (result >= 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("modifyChatFilePath error");

			return false;

		}

	}

	@Override
	public boolean joinChat(int empId, int chatId) {
		// TODO Auto-generated method stub

		String sql = "insert into chat_users (user_id, employee_id, chat_id)" + " values(chat_user_seq.NEXTVAL, ?, ?)";

		try {
			pst = con.prepareStatement(sql);

			pst.setInt(1, empId);
			pst.setInt(2, chatId);

			int result = pst.executeUpdate();

			if (result >= 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("joinChat error");

			return false;

		}

	}

	@Override
	public List<ChatUserDTO> findChatUser(Map<String, Object> checkMap) {
		// TODO Auto-generated method stub


		try {
//			pst = con.prepareStatement(sql);
			
			String type = (String) checkMap.get("type");
			int value = (Integer) checkMap.get("value");
			String sql = "select * from chat_users where "+type+"= ?";
			pst = new LoggableStatement(con, sql);


			pst.setInt(1, value);
			
			

			 

//			System.out.println("\t sQuery ? " +
//			                                    ((LoggableStatement)pst).getQueryString() + "\n");

			 
			
			rs = pst.executeQuery();

			List<ChatUserDTO> userList = null;
			while (rs.next()) {

				ChatUserDTO user = new ChatUserDTO(rs.getInt("user_i"), rs.getInt("employee_id"), rs.getInt("chat_id"));

				userList.add(user);

			}

			return userList;

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("findChatUser error");

			return null;

		}

	}
	
	public List<FileDTO> findFileList(int chatId) { // �߰�����
		String sql = "select * from files where chat_id = ?";
		try {
			pst = con.prepareStatement(sql);

			pst.setInt(1, chatId);

			rs = pst.executeQuery();
			
			List<FileDTO> fileList = new ArrayList<FileDTO>();
			while (rs.next()) {

				FileDTO file = new FileDTO();
				file.setChatId(chatId);
				file.setEmployeeId(rs.getInt("employee_id"));
				file.setFileName(rs.getString("file_name"));
				file.setFilePath(rs.getString("file_path"));
				file.setFileId(rs.getInt("file_id"));	
				file.setUploadTime(rs.getDate("upload_time"));
				fileList.add(file);
			}			
			return fileList;

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("findFileList error");

			return null;

		}

	};

	@Override
	public boolean saveFile(FileDTO file) {
		// TODO Auto-generated method stub

		String sql = "insert into files(file_id, file_path, employee_id, chat_id, file_name)"
				+ " values(file_seq_NEXTVAL, ?, ?, ?, ?)";

		try {
			pst = con.prepareStatement(sql);

			pst.setString(1, file.getFilePath());
			pst.setInt(2, file.getEmployeeId());
			pst.setInt(3, file.getChatId());
			pst.setString(4, file.getFileName());

			int result = pst.executeUpdate();

			if (result >= 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("saveFile error");

			return false;

		}

	}

	@Override
	public FileDTO findFile(FileDTO file) {
		String sql = "select * from files where file_id = ?";

		try {
			pst = con.prepareStatement(sql);

			pst.setInt(1, file.getFileId());

			rs = pst.executeQuery();
			rs.next();
			file.setEmployeeId(rs.getInt("employee_id"));
			file.setChatId(rs.getInt("chat_id"));
			file.setFileName(rs.getString("file_name"));
			file.setFilePath(rs.getString("file_path"));

			return file;

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("findFile error");

			return null;

		}

	};

	@Override
	public DepDTO findDepartments(int depId) {
		// TODO Auto-generated method stub

		String sql = "select * from departments where department_id = ?";

		try {
			pst = con.prepareStatement(sql);

			pst.setInt(1, depId);

			rs = pst.executeQuery();
			rs.next();
			DepDTO dep = new DepDTO();

			dep.setDepartmentId(depId);
			dep.setDepartmentName(rs.getString("department_name"));

			return dep;

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("findDepartments error");

			return null;

		}

	}

	@Override
	public boolean deleteDepartments(int depId) {
		// TODO Auto-generated method stub

		String sql = "delete departments where department_id = ?";

		try {
			pst = con.prepareStatement(sql);

			pst.setInt(1, depId);

			int result = pst.executeUpdate();

			if (result >= 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("deleteDepartments error");

			return false;

		}

	}

	@Override
	public boolean modifyDepartments(DepDTO dep) {
		// TODO Auto-generated method stub

		String sql = "update departments set (department_id, department_name) = (?, ?)";

		try {
			pst = con.prepareStatement(sql);

			pst.setInt(1, dep.getDepartmentId());
			pst.setString(2, dep.getDepartmentName());

			int result = pst.executeUpdate();

			if (result >= 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("modifyDepartments error");

			return false;

		}

	}

}
