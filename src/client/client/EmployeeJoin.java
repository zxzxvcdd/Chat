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
	
	public class EmployeeJoin extends JFrame implements Runnable {
	
		private JPanel contentPane;
		private JTextField employeed_id_text;
		private JTextField name_text;
		private JTextField department_id_text;
		private JTextField job_title_text;
		private JLabel lbl_tel;
		private JPasswordField pw_Field;
		private JPasswordField pw_check_Field;
		private JTextField tel_text;
	
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
		 * 
		 * @throws ParseException
		 */
		public EmployeeJoin(ObjectOutputStream oos) {
	
			this.oos = oos;
			reqMap = null;
	
			setTitle("�쉶�썝媛��엯");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 751, 740);
			contentPane = new JPanel();
			contentPane.setBackground(UIManager.getColor("InternalFrame.inactiveBorderColor"));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	
			setContentPane(contentPane);
			contentPane.setLayout(null);
	
			JLabel lbl_employeed_id = new JLabel("�궗�썝踰덊샇");
			lbl_employeed_id.setFont(new Font("援대┝", Font.BOLD, 15));
			lbl_employeed_id.setBounds(42, 38, 89, 48);
			contentPane.add(lbl_employeed_id);
	
			employeed_id_text = new JTextField();
			employeed_id_text.setBackground(UIManager.getColor("TextField.highlight"));
			employeed_id_text.setBounds(263, 45, 190, 36);
			contentPane.add(employeed_id_text);
			employeed_id_text.setColumns(10);
	
			JLabel lbl_pw = new JLabel("鍮꾨�踰덊샇");
			lbl_pw.setFont(new Font("援대┝", Font.BOLD, 15));
			lbl_pw.setBounds(42, 113, 89, 48);
			contentPane.add(lbl_pw);
	
			pw_Field = new JPasswordField();
			pw_Field.setBackground(UIManager.getColor("TextField.highlight"));
			pw_Field.setBounds(263, 121, 190, 34);
			contentPane.add(pw_Field);
	
			JLabel lbl_check_pw = new JLabel("鍮꾨�踰덊샇 �솗�씤");
			lbl_check_pw.setFont(new Font("援대┝", Font.BOLD, 15));
			lbl_check_pw.setBounds(42, 187, 125, 48);
			contentPane.add(lbl_check_pw);
	
			pw_check_Field = new JPasswordField();
			pw_check_Field.setBackground(UIManager.getColor("TextField.highlight"));
			pw_check_Field.setBounds(263, 195, 190, 34);
			contentPane.add(pw_check_Field);
	
			JLabel lbl_name = new JLabel("�씠由�");
			lbl_name.setFont(new Font("援대┝", Font.BOLD, 15));
			lbl_name.setBounds(42, 267, 125, 48);
			contentPane.add(lbl_name);
	
			name_text = new JTextField();
			name_text.setBackground(UIManager.getColor("TextField.highlight"));
			name_text.setColumns(10);
			name_text.setBounds(263, 274, 190, 36);
			contentPane.add(name_text);
	
			JLabel lbl_department_id = new JLabel("遺��꽌 �븘�씠�뵒");
			lbl_department_id.setFont(new Font("援대┝", Font.BOLD, 15));
			lbl_department_id.setBounds(42, 346, 125, 48);
			contentPane.add(lbl_department_id);
	
			department_id_text = new JTextField();
			department_id_text.setBackground(UIManager.getColor("TextField.highlight"));
			department_id_text.setColumns(10);
			department_id_text.setBounds(263, 353, 190, 36);
			contentPane.add(department_id_text);
	
			lbl_tel = new JLabel("�쟾�솕踰덊샇");
			lbl_tel.setFont(new Font("援대┝", Font.BOLD, 15));
			lbl_tel.setBounds(42, 428, 125, 48);
			contentPane.add(lbl_tel);
	
			tel_text = new JTextField();
			tel_text.setBackground(UIManager.getColor("TextField.highlight"));
			tel_text.setColumns(10);
			tel_text.setBounds(263, 435, 190, 36);
			contentPane.add(tel_text);
	
			JLabel lbl_job_title = new JLabel("吏곸콉");
			lbl_job_title.setFont(new Font("援대┝", Font.BOLD, 15));
			lbl_job_title.setBounds(42, 503, 125, 48);
			contentPane.add(lbl_job_title);
	
			job_title_text = new JTextField();
			job_title_text.setBackground(UIManager.getColor("TextField.highlight"));
			job_title_text.setColumns(10);
			job_title_text.setBounds(263, 510, 190, 36);
			contentPane.add(job_title_text);
	
			JButton btnNewButton = new JButton("�쉶�썝媛��엯");
			btnNewButton.setBackground(UIManager.getColor("Button.light"));
			btnNewButton.addMouseListener(new MouseAdapter() {
				@SuppressWarnings("deprecation")
				@Override
				public void mouseClicked(MouseEvent e) {
	
					// ----癒쇱� �엯�젰 媛믪쓣 String �옄猷뚰삎�쑝濡� �꽆寃� 諛쏅뒗�떎
	
					System.out.println("join 이벤트");
	
					String employee_id = null; // �궗踰�
					String pw = null; // �뙣�뒪�썙�뱶
					String pw_check = null; // �뙣�뒪�썙�뱶 泥댄겕
					String name = null; // �씠由�
					String department_id = null; // 遺��꽌踰덊샇
					String tel = null; // �쟾�솕踰덊샇
					String job_title = null; // 吏곸콉
	
					// ---- 洹몃윴�떎�쓬 Pattern.matches()濡� �젙洹쒗몴�쁽�떇怨� �엯�젰 媛믪쓣
					// 鍮꾧탳 �븯怨� boolean�삎�쑝濡� �꽆寃⑥��떎
	
					boolean id_pt;
					boolean pw_pt;
					boolean name_pt;
					boolean department_pt;
					boolean tel_pt;
					boolean title_pt;
	
					employee_id = employeed_id_text.getText();
					id_pt = Pattern.matches("^[0-9]*$", employee_id); // �닽�옄留� 媛��뒫�빀�땲�떎
	
					pw = pw_Field.getText();
					pw_pt = Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])" // �닽�옄,臾몄옄,�듅�닔臾몄옄 �룷�븿 8�옄 �씠�긽
							+ "[A-Za-z\\d$@$!%*#?&]{8,}$", pw);
	
					pw_check = pw_check_Field.getText();
	
					name = name_text.getText();
					name_pt = Pattern.matches("^[媛�-�옡]*$", name); // �븳湲�留� 媛��뒫�빀�땲�떎
	
					department_id = department_id_text.getText();
					department_pt = Pattern.matches("^[0-9]*$", department_id);
	
					tel = tel_text.getText();
					tel_pt = Pattern.matches("^\\d{2,3}-\\d{3,4}-\\d{4}$" // �닽�옄�옉 (-)�븯�씠�뵂 �엯�젰留� 媛��뒫�빀�땲�떎
					, tel);
	
					job_title = job_title_text.getText();
					title_pt = Pattern.matches("^[媛�-�옡]*$", job_title); // �븳湲�留� 媛��뒫�빀�땲�떎
	
					// ---------議띻퇋�몴�쁽�떇怨� 鍮꾧탳 �썑 false�씠硫� �뿉�윭 硫붿떆吏� 李� �샇異�
	
	// 				if(id_pt == false) {
	// 					JOptionPane.showMessageDialog(null, "�븘�씠�뵒瑜� �솗�씤�빐二쇱꽭�슂(�궗�썝踰덊샇留� 媛��뒫�빀�땲�떎"
	// 							, "ID ERROR", JOptionPane.ERROR_MESSAGE);
	// 				}else if(pw_pt == false) {
	// 					JOptionPane.showMessageDialog(null, "鍮꾨�踰덊샇瑜� �솗�씤�빐 二쇱꽭�슂 �닽�옄, 臾몄옄, �듅�닔臾몄옄 �룷�븿 8�옄�씠�긽",
	// 					"Password ERROE", JOptionPane.ERROR_MESSAGE);
	// 				}else if(!pw_check.equals(pw) ) {
	// 					JOptionPane.showMessageDialog(null, "鍮꾨�踰덊샇媛� �씪移섑븯吏� �븡�뒿�땲�떎",
	// 							"Password Check ERROR",JOptionPane.ERROR_MESSAGE);
	// 				}else if(name_pt == false) {
	// 					JOptionPane.showMessageDialog(null, "�븳湲�留� 媛��뒫�빀�땲�떎",
	// 							"Name ERROE",JOptionPane.ERROR_MESSAGE);
	// 				}else if(department_pt == false) {
	// 					JOptionPane.showMessageDialog(null, "遺��꽌踰덊샇留� �엯�젰�빐二쇱꽭�슂",
	// 							"Departmnet ID ERROR",JOptionPane.ERROR_MESSAGE);
	// 				}else if(tel_pt == false) {
	// 					JOptionPane.showMessageDialog(null, "�쟾�솕踰덊샇留� �엯�젰�빐二쇱꽭�슂",
	// 							"Phone Number ERROR",JOptionPane.ERROR_MESSAGE);
	//				}else if(title_pt == false) {
	// 					JOptionPane.showMessageDialog(null, "�븳湲�留� 媛��뒫�빀�땲�떎",
	// 							"Job Title ERROR",JOptionPane.ERROR_MESSAGE);
	// 				}else { // ------
	
					int id = Integer.parseInt(employee_id);
					int d_id = Integer.parseInt(department_id);
	
					String command = "join";
	
					reqMap = new HashMap<Object, Object>();
					reqMap.put("command", command);
	
					EmpDTO empDTO = new EmpDTO(id, pw, name, d_id, tel, null, job_title); // 媛믪쓣 EmpDTO濡� �꽆湲대떎
					System.out.println(empDTO);
					reqMap.put("emp", empDTO);
	
					try {
						oos.writeObject(reqMap);
						oos.flush();
					} catch (Exception e1) {
						// TODO: handle exception
					}
	
	// 					ClientMain cm = new ClientMain();	//ClientMain 媛앹껜瑜� �깮�꽦�븳�떎
	// 					String s = cm.check_Join(empdto);
	
	// 				}
	
				}
			});
			btnNewButton.setFont(new Font("援대┝", Font.BOLD, 18));
			btnNewButton.setBounds(270, 610, 208, 70);
			contentPane.add(btnNewButton);
	
			Thread t1 = new Thread(this);
			t1.start();
	
		}
	
		@Override
		public void run() {
	
			try {
				while (true) {
	
					if (call) {
	
						System.out.println("resMap:" + resMap);
						if (resMap != null) {
	
							boolean result = (boolean) resMap.get("joinResult");
							System.out.println(result);
							if (result) {
								// ------�쐞 �옉�뾽�씠 �걹�굹硫� �쉶�썝媛��엯 李쎌쓣 �떕怨� 濡쒓렇�씤李쎌쓣 �쓣�슫�떎 dispose();
								dispose();
								setVisible(false);
								JOptionPane.showMessageDialog(null, "회원가입 성공!", null, JOptionPane.PLAIN_MESSAGE);
	
								new Login(oos).setVisible(true);
	
							} else {
								JOptionPane.showMessageDialog(null, "회원가입 실패", null, JOptionPane.ERROR_MESSAGE);
							}
	
							call = false;
	
						}
					}else System.out.println(call);
	
				}
	
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	
	}
