package client.clientDTO;

public class DepDTO {

	
	private int DepartmentId;
	private String DepartmentName;
	
	
	public DepDTO() {
		// TODO Auto-generated constructor stub
	}


	public DepDTO(int departmentId, String departmentName) {
		super();
		DepartmentId = departmentId;
		DepartmentName = departmentName;
	}


	public int getDepartmentId() {
		return DepartmentId;
	}


	public void setDepartmentId(int departmentId) {
		DepartmentId = departmentId;
	}


	public String getDepartmentName() {
		return DepartmentName;
	}


	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}


	@Override
	public String toString() {
		return "DepDTO [DepartmentId=" + DepartmentId + ", DepartmentName=" + DepartmentName + "]";
	}
	
	
	
	
	
	
	
	
}
