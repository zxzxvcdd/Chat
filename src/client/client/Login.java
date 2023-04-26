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
	
	EmpDTO emp = new EmpDTO();

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

		setTitle("�α���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 755, 729);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel login_lbl = new JLabel("�α���");
		login_lbl.setHorizontalAlignment(JLabel.CENTER); // "로그?��"?�� 중앙?���? ?���?
//		login_lbl.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 25));
		login_lbl.setBounds(238, 25, 267, 63);
		contentPane.add(login_lbl);

		id_textField = new JTextField();
		id_textField.setBounds(208, 157, 333, 63);
		contentPane.add(id_textField);
		id_textField.setColumns(10);

		JLabel id_lbl = new JLabel("���̵�");
		id_lbl.setFont(new Font("����", Font.PLAIN, 12));
		id_lbl.setHorizontalAlignment(JLabel.CENTER);
		id_lbl.setFont(new Font("����", Font.BOLD, 17));
		id_lbl.setBounds(286, 242, 163, 41);
		contentPane.add(id_lbl);

		pw_textField = new JTextField();
		pw_textField.setColumns(10);
		pw_textField.setBounds(208, 315, 333, 63);
		contentPane.add(pw_textField);

		JLabel pw_lbl = new JLabel("��й�ȣ");
		pw_lbl.setHorizontalAlignment(JLabel.CENTER);
		pw_lbl.setFont(new Font("����", Font.BOLD, 17));
		pw_lbl.setBounds(286, 415, 163, 41);
		contentPane.add(pw_lbl);

		JButton btnNewButton = new JButton("�α���");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				System.out.println("�α����̺�Ʈ");
				int id = Integer.parseInt(id_textField.getText());
				String pw = pw_textField.getText();
				System.out.println(id + pw);

				String command = "login";

				reqMap = new HashMap<Object, Object>();

				reqMap.put("command", command);

				
				emp.setEmployeeId(id);
				emp.setPw(pw);

				reqMap.put("emp", emp);

				try {
					oos.writeObject(reqMap);
					oos.flush();
				} catch (Exception e1) {

				}

				// ClientMain cm = new ClientMain();

				// 로그?�� ?��?��?��?�� 기능 구현 ?���? ?��?��

//					}
//						else {
//						int login_cnt = 5;
//						
//						login_cnt -= i;
//						String message = "로그?�� ?��?��\n" + "?��?? ?��?��?�� " + Integer.toString(login_cnt)
//						+ "�? ?��?��?��?��?��.";
//						JOptionPane.showMessageDialog(null, message,
//								"Message",JOptionPane.ERROR_MESSAGE);
//					}
			}

		});
		btnNewButton.setFont(new Font("����", Font.BOLD, 20));
		btnNewButton.setBounds(267, 499, 210, 41);
		contentPane.add(btnNewButton);

//		JButton btnNewButton_1 = new JButton("��������");
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
//		btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 17));
//		btnNewButton_1.setBounds(267, 622, 210, 41);
//		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("ȸ������");
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
		btnNewButton_2.setFont(new Font("굴림", Font.BOLD, 20));
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
						JOptionPane.showMessageDialog(null, "�α��μ���");
						emp = (EmpDTO) resMap.get("emp");
						dispose();
						setVisible(false);
						
						new MainViewFrame(oos,emp).setVisible(true);
						
						// 로그?�� ?���? �? ?��?�� 메인?��면으�? ?���?
					} else {
						JOptionPane.showMessageDialog(null, "�α��� ����", "Message",
								JOptionPane.ERROR_MESSAGE);

					}

					call = false;

				}else System.out.print("");

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
