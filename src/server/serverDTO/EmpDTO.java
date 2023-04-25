package server.serverDTO;
import java.io.Serializable;

public class EmpDTO implements Serializable {
	
	private static final long serialVersionUID = 123L;
	
	private int employeeId;
	private String pw;
	private String name;
	private int departmentId;
	private String tel;
	private String admin;
	private String jobTitle;
	private String departmentName;
	
	public EmpDTO() {
		// TODO Auto-generated constructor stub
	}

	public EmpDTO(int employeeId, String pw, String name, int departmentId, String tel, String admin, String jobTitle) {
		super();
		this.employeeId = employeeId;
		this.pw = pw;
		this.name = name;
		this.departmentId = departmentId;
		this.tel = tel;
		this.admin = admin;
		this.jobTitle = jobTitle;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Override
	public String toString() {
		return "EmpDTO [employeeId=" + employeeId + ", pw=" + pw + ", name=" + name + ", departmentId=" + departmentId
				+ ", tel=" + tel + ", admin=" + admin + ", jobTitle=" + jobTitle + "]";
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
