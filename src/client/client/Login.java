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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import server.serverDTO.EmpDTO;

public class Login extends JFrame implements Runnable {

	private JPanel contentPane;
	private JTextField id_textField;
	private JTextField pw_textField;

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

		this.oos = oos;

		reqMap = null;

		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 755, 729);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel login_lbl = new JLabel("로그인");
		login_lbl.setHorizontalAlignment(JLabel.CENTER); // "濡쒓렇?씤"?쓣 以묒븰?쑝濡? ?삷源?
//		login_lbl.setFont(new Font("援대┝", Font.BOLD | Font.ITALIC, 25));
		login_lbl.setBounds(238, 25, 267, 63);
		contentPane.add(login_lbl);

		id_textField = new JTextField();
		id_textField.setBounds(208, 157, 333, 63);
		contentPane.add(id_textField);
		id_textField.setColumns(10);

		JLabel id_lbl = new JLabel("아이디");
		id_lbl.setFont(new Font("굴림", Font.PLAIN, 12));
		id_lbl.setHorizontalAlignment(JLabel.CENTER);
		id_lbl.setFont(new Font("굴림", Font.BOLD, 17));
		id_lbl.setBounds(286, 242, 163, 41);
		contentPane.add(id_lbl);

		pw_textField = new JTextField();
		pw_textField.setColumns(10);
		pw_textField.setBounds(208, 315, 333, 63);
		contentPane.add(pw_textField);

		JLabel pw_lbl = new JLabel("비밀번호");
		pw_lbl.setHorizontalAlignment(JLabel.CENTER);
		pw_lbl.setFont(new Font("굴림", Font.BOLD, 17));
		pw_lbl.setBounds(286, 415, 163, 41);
		contentPane.add(pw_lbl);

		JButton btnNewButton = new JButton("로그인");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				System.out.println("로그인이벤트");
				int id = Integer.parseInt(id_textField.getText());
				String pw = pw_textField.getText();
				System.out.println(id + pw);

				String command = "login";

				reqMap = new HashMap<Object, Object>();

				reqMap.put("command", command);

				EmpDTO emp = new EmpDTO();
				emp.setEmployeeId(id);
				emp.setPw(pw);

				reqMap.put("emp", emp);

				try {
					oos.writeObject(reqMap);
					oos.flush();
				} catch (Exception e1) {

				}

				// ClientMain cm = new ClientMain();

				// 濡쒓렇?씤 ?슏?닔?젣?븳 湲곕뒫 援ы쁽 ?븘吏? ?븞?맖

//					}
//						else {
//						int login_cnt = 5;
//						
//						login_cnt -= i;
//						String message = "濡쒓렇?씤 ?떎?뙣\n" + "?궓?? ?슏?닔?뒗 " + Integer.toString(login_cnt)
//						+ "踰? ?궓?븯?뒿?땲?떎.";
//						JOptionPane.showMessageDialog(null, message,
//								"Message",JOptionPane.ERROR_MESSAGE);
//					}
			}

		});
		btnNewButton.setFont(new Font("굴림", Font.BOLD, 20));
		btnNewButton.setBounds(267, 499, 210, 41);
		contentPane.add(btnNewButton);

//		JButton btnNewButton_1 = new JButton("정보수정");
//		btnNewButton_1.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//
//				dispose();
//				setVisible(true);
////				new Accreditation(oos).setVisible(true);
//
//			}
//		});
//		btnNewButton_1.setFont(new Font("援대┝", Font.BOLD, 17));
//		btnNewButton_1.setBounds(267, 622, 210, 41);
//		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("회원가입");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				dispose();
				setVisible(false);
				try {
					new EmployeeJoin(oos).setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					// e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setFont(new Font("援대┝", Font.BOLD, 20));
		btnNewButton_2.setBounds(267, 565, 210, 41);
		contentPane.add(btnNewButton_2);

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

					System.out.println(resMap.size());
					boolean result = (boolean) resMap.get("loginResult");

					System.out.println(result);
					if (result) { // cm = ClientMain
						JOptionPane.showMessageDialog(null, "濡쒓렇?씤 ?릺?뿀?뒿?땲?떎!");
						// 濡쒓렇?씤 ?릺硫? 洹? ?떎?쓬 硫붿씤?쉶硫댁쑝濡? ?뿰寃?
					} else {
						JOptionPane.showMessageDialog(null, "?븘?씠?뵒 ?삉?뒗 鍮꾨?踰덊샇媛? ???졇?뒿?땲?떎", "Message",
								JOptionPane.ERROR_MESSAGE);

					}

					call = false;

				} else
					System.out.println(call);

			}

		} catch (Exception e) {

		}

	}

}
