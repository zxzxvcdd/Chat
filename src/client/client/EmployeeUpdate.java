package client.client;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import server.serverDTO.EmpDTO;

public class EmployeeUpdate extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTextField employeeId_textField;
	private JTextField name_textField;
	private JTextField departmentId_textField;
	private JTextField jobTitle_textField;
	private JLabel tel_lbl;
	private JPasswordField pw_textField;
	private JPasswordField checkPw_textField;
	private JTextField tel_textField;
	
	ObjectOutputStream oos;
	HashMap<Object, Object> reqMap;
	
	public static boolean call = false;
	public static HashMap<Object, Object> resMap = null;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Employee_Join frame = new Employee_Join();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @throws ParseException 
	 */
	public EmployeeUpdate(ObjectOutputStream oos) {
		
		this.oos = oos;
		reqMap = null;
		
		setTitle("정보수정");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 751, 740);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("InternalFrame.inactiveBorderColor"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel employeeId_lbl = new JLabel("사원번호");
		employeeId_lbl.setFont(new Font("굴림", Font.BOLD, 15));
		employeeId_lbl.setBounds(42, 38, 89, 48);
		contentPane.add(employeeId_lbl);
		
		employeeId_textField = new JTextField();
		employeeId_textField.setBackground(UIManager.getColor("TextField.highlight"));
		employeeId_textField.setBounds(263, 45, 190, 36);
		contentPane.add(employeeId_textField);
		employeeId_textField.setColumns(10);
		
		JLabel pw_lbl = new JLabel("비밀번호");
		pw_lbl.setFont(new Font("굴림", Font.BOLD, 15));
		pw_lbl.setBounds(42, 113, 89, 48);
		contentPane.add(pw_lbl);
		
		pw_textField = new JPasswordField();
		pw_textField.setBackground(UIManager.getColor("TextField.highlight"));
		pw_textField.setBounds(263, 121, 190, 34);
		contentPane.add(pw_textField);
		
		JLabel checkPw_lbl = new JLabel("비밀번호 확인");
		checkPw_lbl.setFont(new Font("굴림", Font.BOLD, 15));
		checkPw_lbl.setBounds(42, 187, 125, 48);
		contentPane.add(checkPw_lbl);
		
		checkPw_textField = new JPasswordField();
		checkPw_textField.setBackground(UIManager.getColor("TextField.highlight"));
		checkPw_textField.setBounds(263, 195, 190, 34);
		contentPane.add(checkPw_textField);
		
		JLabel name_lbl = new JLabel("이름");
		name_lbl.setFont(new Font("굴림", Font.BOLD, 15));
		name_lbl.setBounds(42, 267, 125, 48);
		contentPane.add(name_lbl);
		
		name_textField = new JTextField();
		name_textField.setBackground(UIManager.getColor("TextField.highlight"));
		name_textField.setColumns(10);
		name_textField.setBounds(263, 274, 190, 36);
		contentPane.add(name_textField);
		
		JLabel departmentId_lbl = new JLabel("부서 아이디");
		departmentId_lbl.setFont(new Font("굴림", Font.BOLD, 15));
		departmentId_lbl.setBounds(42, 346, 125, 48);
		contentPane.add(departmentId_lbl);
		
		departmentId_textField = new JTextField();
		departmentId_textField.setBackground(UIManager.getColor("TextField.highlight"));
		departmentId_textField.setColumns(10);
		departmentId_textField.setBounds(263, 353, 190, 36);
		contentPane.add(departmentId_textField);
		
		tel_lbl = new JLabel("전화번호");
		tel_lbl.setFont(new Font("굴림", Font.BOLD, 15));
		tel_lbl.setBounds(42, 428, 125, 48);
		contentPane.add(tel_lbl);
		
		tel_textField = new JTextField();
		tel_textField.setBackground(UIManager.getColor("TextField.highlight"));
		tel_textField.setColumns(10);
		tel_textField.setBounds(263, 435, 190, 36);
		contentPane.add(tel_textField);
		
		JLabel jobTitle_lbl = new JLabel("직책");
		jobTitle_lbl.setFont(new Font("굴림", Font.BOLD, 15));
		jobTitle_lbl.setBounds(42, 503, 125, 48);
		contentPane.add(jobTitle_lbl);
		
		jobTitle_textField = new JTextField();
		jobTitle_textField.setBackground(UIManager.getColor("TextField.highlight"));
		jobTitle_textField.setColumns(10);
		jobTitle_textField.setBounds(263, 510, 190, 36);
		contentPane.add(jobTitle_textField);
		
		JButton bupdate_Button = new JButton("수정");
		bupdate_Button.setBackground(UIManager.getColor("Button.light"));
		bupdate_Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//	----먼저 입력 값을 String 자료형으로 넘겨 받는다
				
				String employee_id=null;	//사번
				String pw=null;				//패스워드
				String pw_check=null;		//패스워드 체크
				String name=null;			//이름
				String department_id=null;	//부서번호
				String tel=null;			//전화번호
				String job_title=null;		//직책
				
				// 	---- 그런다음 Pattern.matches()로 정규표현식과 입력 값을 
				//비교 하고 boolean형으로 넘겨준다
				
				boolean id_pt;
				boolean pw_pt;
				boolean name_pt;
				boolean department_pt;
				boolean tel_pt;
				boolean title_pt;
				
				employee_id = employeeId_textField.getText();
				id_pt = Pattern.matches("^[0-9]*$", employee_id);	// 숫자만 가능합니다
				
				pw = pw_textField.getText();
				pw_pt = Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])"		//숫자,문자,특수문자 포함 8자 이상
						+ "[A-Za-z\\d$@$!%*#?&]{8,}$", pw);
				
				pw_check = checkPw_textField.getText();
				
				name = name_textField.getText();
				name_pt = Pattern.matches("^[가-힣]*$", name);		// 한글만 가능합니다
				
				department_id = departmentId_textField.getText();
				department_pt = Pattern.matches("^[0-9]*$", department_id);
				
				tel = tel_textField.getText();
				tel_pt = Pattern.matches("^\\d{2,3}-\\d{3,4}-\\d{4}$"	//숫자랑 (-)하이픈 입력만 가능합니다
						, tel);
				
				job_title = jobTitle_textField.getText();
 				title_pt = Pattern.matches("^[가-힣]*$", job_title);		//한글만 가능합니다
 				
 				//---------졍규표현식과 비교 후 false이면 에러 메시지 창 호출
 				
 				if(id_pt == false) {
 					JOptionPane.showMessageDialog(null, "아이디를 확인해주세요(사원번호만 가능합니다"
 							, "ID ERROR", JOptionPane.ERROR_MESSAGE);
 				}else if(pw_pt == false) {
 					JOptionPane.showMessageDialog(null, "비밀번호를 확인해 주세요 숫자, 문자, 특수문자 포함 8자이상",
 					"Password ERROE", JOptionPane.ERROR_MESSAGE);
 				}else if(!pw_check.equals(pw) ) {
 					JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다",
 							"Password Check ERROR",JOptionPane.ERROR_MESSAGE);
 				}else if(name_pt == false) {
 					JOptionPane.showMessageDialog(null, "한글만 가능합니다",
 							"Name ERROE",JOptionPane.ERROR_MESSAGE);
 				}else if(department_pt == false) {
 					JOptionPane.showMessageDialog(null, "부서번호만 입력해주세요",
 							"Departmnet ID ERROR",JOptionPane.ERROR_MESSAGE);
 				}else if(tel_pt == false) {
 					JOptionPane.showMessageDialog(null, "전화번호만 입력해주세요",
 							"Phone Number ERROR",JOptionPane.ERROR_MESSAGE);
				}else if(title_pt == false) {
 					JOptionPane.showMessageDialog(null, "한글만 가능합니다",
 							"Job Title ERROR",JOptionPane.ERROR_MESSAGE);
 				}else { // ------
 					
 					int id = Integer.parseInt(employee_id);
 					int d_id = Integer.parseInt(department_id);
 					
 					String command = "update";
 					
 					reqMap = new HashMap<Object, Object>();
 					reqMap.put("command", command);
 					
 					EmpDTO empdto = new EmpDTO();
 					empdto.setPw(pw);
 					empdto.setName(name);
 					empdto.setDepartmentId(d_id); 		
 					empdto.setTel(tel);
 					empdto.setJobTitle(job_title);
 					empdto.setEmployeeId(id);
 					
 					reqMap.put("emp", empdto);
 					
 					try {
 						oos.writeObject(reqMap);
 						oos.flush();
 					}catch (Exception e1) {
						// TODO: handle exception
					}
 					
 					
 				}
 				
 				
				
			}
		});
		bupdate_Button.setFont(new Font("굴림", Font.BOLD, 18));
		bupdate_Button.setBounds(270, 610, 208, 70);
		contentPane.add(bupdate_Button);
		
		Thread t1 = new Thread(this);
		t1.start();
		
	}
	
	public void run() {
		
		try {
			
			while(true) {
				
				if(call) {
					

						boolean result = (boolean) resMap.get("Updateresult");
						
						if(result) { 
							
						dispose();
						  setVisible(false); 
						  JOptionPane.showMessageDialog(null, "정보수정 완료!", null,
						  JOptionPane.PLAIN_MESSAGE);
						  //메인화면 띄움
						  new Login(oos);
						  
						  }else {
							  JOptionPane.showMessageDialog(null, "정보수정 실패!", null,
									  JOptionPane.ERROR_MESSAGE);
						  }
						
						call = false;
						
						
				}else System.out.printf("");
				
			}
			
		
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
}
		
	
