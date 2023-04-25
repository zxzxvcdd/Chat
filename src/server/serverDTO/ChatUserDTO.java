package server.serverDTO;

public class ChatUserDTO {

	
	private int userId;
	private int employeeId;
	private int chatId;
	private String name;
	
	
	public ChatUserDTO() {
		// TODO Auto-generated constructor stub
	}
	

	



	public ChatUserDTO(int userId, int employeeId, int chatId, String name) {
		super();
		this.userId = userId;
		this.employeeId = employeeId;
		this.chatId = chatId;
		this.name = name;
	}




	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getChatId() {
		return chatId;
	}
	public void setChatId(int chatId) {
		this.chatId = chatId;
	}





	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
	
	
	
}
