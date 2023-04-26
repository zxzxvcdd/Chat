package client.client;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectOutputStream;
import java.util.HashMap;

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

public class Login extends JFrame implements Runnable {

	private JPanel contentPane;
	private JTextField id_textField;
	
	ObjectOutputStream oos;
	
	HashMap<Object,Object> reqMap;
	
	
	
	public static boolean call = false;
	public static HashMap<Object,Object> resMap =null;
	private JPasswordField pw_textField;
	private EmpDTO emp;
	
	
	

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Login frame = new Login();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Login(ObjectOutputStream oos) {
		
		
		
		this.oos =oos;
		
		reqMap= null;
		
		
		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 755, 729);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel login_lbl = new JLabel("로그인");
		login_lbl.setHorizontalAlignment(JLabel.CENTER);		//"로그인"을 중앙으로 옮김
		login_lbl.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 25));
		login_lbl.setBounds(238, 25, 267, 63);
		contentPane.add(login_lbl);
		
		id_textField = new JTextField();
		id_textField.setBounds(208, 157, 333, 63);
		contentPane.add(id_textField);
		id_textField.setColumns(10);
		
		JLabel id_lbl = new JLabel("아이디");
		id_lbl.setHorizontalAlignment(JLabel.CENTER);
		id_lbl.setFont(new Font("굴림", Font.BOLD, 17));
		id_lbl.setBounds(286, 242, 163, 41);
		contentPane.add(id_lbl);
		
		pw_textField = new JPasswordField();
		pw_textField.setBounds(211, 300, 330, 69);
		contentPane.add(pw_textField);
		
		JLabel pw_lbl = new JLabel("비밀번호");
		pw_lbl.setHorizontalAlignment(JLabel.CENTER);
		pw_lbl.setFont(new Font("굴림", Font.BOLD, 17));
		pw_lbl.setBounds(286, 407, 163, 41);
		contentPane.add(pw_lbl);
		
		JButton login_Button = new JButton("로그인");
		login_Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				System.out.println("로그인이벤트");
				int id = Integer.parseInt(id_textField.getText());
				String pw = pw_textField.getText();
				System.out.println(id+pw);
			
				
				String command = "login";
				

				reqMap = new HashMap<Object,Object>();
				
				reqMap.put("command", command);
				
				EmpDTO emp = new EmpDTO();
				emp.setEmployeeId(id);
				emp.setPw(pw);
				
				
				reqMap.put("emp", emp);
				
				try {
					oos.writeObject(reqMap);
					oos.flush();
				}catch(Exception e1) {
					
				}
				
				
				
				
				//ClientMain cm = new ClientMain();
				
				
				 // 로그인 횟수제한 기능 구현 아직 안됨

//					}
//						else {
//						int login_cnt = 5;
//						
//						login_cnt -= i;
//						String message = "로그인 실패\n" + "남은 횟수는 " + Integer.toString(login_cnt)
//						+ "번 남았습니다.";
//						JOptionPane.showMessageDialog(null, message,
//								"Message",JOptionPane.ERROR_MESSAGE);
//					}
				}
				
				
			
		});
		login_Button.setFont(new Font("굴림", Font.BOLD, 20));
		login_Button.setBounds(267, 499, 210, 41);
		contentPane.add(login_Button);
		
		JButton update_Button = new JButton("비밀번호 찾기");
		update_Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				dispose();
				setVisible(true);
				new Accreditation(oos).setVisible(true);
				
			}
		});
		update_Button.setFont(new Font("굴림", Font.BOLD, 17));
		update_Button.setBounds(267, 622, 210, 41);
		contentPane.add(update_Button);
		
		JButton join_Button = new JButton("회원가입");
		join_Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				dispose();
				setVisible(false);
				try {
					new EmployeeJoin(oos).setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
			}
		});
		join_Button.setFont(new Font("굴림", Font.BOLD, 20));
		join_Button.setBounds(267, 565, 210, 41);
		contentPane.add(join_Button);
		
		
		
		setVisible(true);
		
		Thread t1 = new Thread(this);
		t1.start();
		
		
	}




	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		try {

			while (true) {
	
				
				
				if (call) {
					
					
					String resCom = (String) resMap.get("command");

						
						
						boolean result = (boolean) resMap.get("loginResult");
					
						
						
						if (result) { // cm = ClientMain
							JOptionPane.showMessageDialog(null, "로그인 되었습니다!");
							emp = (EmpDTO) resMap.get("emp");
							dispose();
							setVisible(false);
							
							new MainViewFrame(oos,emp).setVisible(true);
							
							// ë¡œê·¸?¸ ?˜ë©? ê·? ?‹¤?Œ ë©”ì¸?šŒë©´ìœ¼ë¡? ?—°ê²?
						} else {
							JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호가 틀렸습니다", "Message",
									JOptionPane.ERROR_MESSAGE);

						}
						
						
						call=false;


					}else
					System.out.printf("");
			}

		} catch (Exception e) {

		}
		
		
		
		
		
		
	}
}
