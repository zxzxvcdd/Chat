package server.serverDTO;

import java.util.Date;

public class FileDTO {

	private int employeeId;
	private int fileId;
	private String fileName;
	private int chatId;
	private String filePath;
	private Date uploadTime;

	public FileDTO() {
		// TODO Auto-generated constructor stub
	}

	public FileDTO(int employeeId, int fileId, String fileName, int chatId, String filePath, Date uploadTime) {
		super();
		this.employeeId = employeeId;
		this.fileId = fileId;
		this.fileName = fileName;
		this.chatId = chatId;
		this.filePath = filePath;
		this.uploadTime = uploadTime;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "FileDTO [employeeId=" + employeeId + ", fileId=" + fileId + ", fileName=" + fileName + ", chatId="
				+ chatId + ", filePath=" + filePath + ", uploadTime=" + uploadTime + "]";
	}

}
