package client.clientDTO;

public class ChatUserDTO {

	
	private int userId;
	private int employeeId;
	private int chatId;
	
	public ChatUserDTO() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ChatUserDTO(int userId, int employeeId, int chatId) {
		super();
		this.userId = userId;
		this.employeeId = employeeId;
		this.chatId = chatId;
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


	@Override
	public String toString() {
		return "ChatUserDTO [userId=" + userId + ", employeeId=" + employeeId + ", chatId=" + chatId + "]";
	}
	
	
	
	
	
}
