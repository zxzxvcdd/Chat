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
import javax.swing.border.EmptyBorder;

import server.serverDTO.EmpDTO;

public class Accreditation extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTextField employeeId_textField;
	private JButton accreditation_Button;
	
	ObjectOutputStream oos;
	HashMap<Object,Object> reqMap;
	
	public static boolean call = false;
	public static HashMap<Object,Object> resMap =null;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Accreditation frame = new Accreditation();
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
	public Accreditation(ObjectOutputStream oos) {
		
		this.oos = oos;
		reqMap = null;
		
		
		setTitle("본인 인증");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 545);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel accreditation_lbl = new JLabel("본인 인증");
		accreditation_lbl.setHorizontalAlignment(JLabel.CENTER);
		accreditation_lbl.setFont(new Font("굴림", Font.BOLD, 20));
		accreditation_lbl.setBounds(134, 33, 296, 63);
		contentPane.add(accreditation_lbl);
		
		employeeId_textField = new JTextField();
		employeeId_textField.setBounds(149, 134, 274, 63);
		contentPane.add(employeeId_textField);
		employeeId_textField.setColumns(10);
		
		JLabel employeeId_lbl = new JLabel("사원 번호");
		employeeId_lbl.setHorizontalAlignment(JLabel.CENTER);
		employeeId_lbl.setFont(new Font("굴림", Font.BOLD, 17));
		employeeId_lbl.setBounds(209, 226, 143, 33);
		contentPane.add(employeeId_lbl);
		
		accreditation_Button = new JButton("인증");
		accreditation_Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String e_id = employeeId_textField.getText();
				int id = Integer.parseInt(e_id);
				
				String command = "accreditation";
				reqMap = new HashMap<Object,Object>();
				
				reqMap.put("command", command);
				
				EmpDTO emp = new EmpDTO();
				emp.setEmployeeId(id);
				
				reqMap.put("emp", emp);
				
				try {
					oos.writeObject(reqMap);
					oos.flush();
				}catch(Exception e1) {
					
				}
				
			}
		});
		accreditation_Button.setFont(new Font("굴림", Font.BOLD, 20));
		accreditation_Button.setBounds(197, 364, 186, 63);
		contentPane.add(accreditation_Button);
		
		Thread t1 = new Thread(this);
		t1.start();
	}

	@Override
	public void run() {
		
		try {
			

			while(true) {
				
				
				if(call) {
	
					
						boolean result = (boolean) resMap.get("accResult");
						
						if(result) {
							JOptionPane.showMessageDialog(null, "인증 되었습니다!");
							dispose();
							setVisible(false);
							new EmployeeUpdate(oos).setVisible(true);
						}else {
							JOptionPane.showMessageDialog(null, "인증 실패했습니다!","ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
						
						call = false;

					
					}else
						System.out.printf("");
				}

		}catch (Exception e) {
			// TODO: handle exception
			
		}
		
		
		
	}

}
