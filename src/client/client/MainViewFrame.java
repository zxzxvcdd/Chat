package client.client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import server.serverDTO.ChatInfo;
import server.serverDTO.ChatListDTO;
import server.serverDTO.EmpDTO;

public class MainViewFrame extends JFrame implements Runnable, ActionListener, ListSelectionListener {

	public static boolean call = false;
	public static HashMap<Object, Object> resMap = null;

	DefaultListModel chatListModel;

	private JPanel contentPane;
	private JTextField empSearchText;
	private JTextField chatSearchText;
	private JTable table1, table2;
	ObjectOutputStream oos;
	DefaultTableModel model1, model2;
	int empId;
	List<ChatInfo> myChatList;
	private JList sawonList;

	/**
	 * Create the frame.
	 */
	public MainViewFrame(ObjectOutputStream oos, int empId) {

		this.oos = oos;
		this.empId= empId;

		System.out.println(empId);

		HashMap<Object, Object> reqMap = new HashMap<Object, Object>();
		String command = "main";

		
		reqMap.put("command", command);
		reqMap.put("type", "employee_id");
		reqMap.put("empId", empId);

		try {
			oos.writeObject(reqMap);
			oos.flush();
		} catch (Exception e) {

		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 681, 599);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// �������� ��� ��
		JLabel onlineSawon = new JLabel("   \uC811\uC18D\uC911\uC778 \uC0AC\uC6D0");
		onlineSawon.setFont(new Font("����", Font.PLAIN, 20));
		onlineSawon.setBounds(466, 77, 171, 40);
		contentPane.add(onlineSawon);

		// �������� ������
		sawonList = new JList();
		sawonList.setBounds(466, 120, 171, 150);
		contentPane.add(sawonList);

		// ������ Label
		JLabel deparLabel = new JLabel("\uC870\uC9C1\uB3C4");
		deparLabel.setFont(new Font("����", Font.PLAIN, 22));
		deparLabel.setBounds(38, 27, 73, 40);
		contentPane.add(deparLabel);

//		//ä�ù� Label
//		JLabel chatListLabel = new JLabel("\uCC44\uD305\uBC29 \uBAA9\uB85D");
//		chatListLabel.setFont(new Font("����", Font.PLAIN, 22));
//		chatListLabel.setBounds(38, 295, 134, 40);
//		contentPane.add(chatListLabel);

		// �游���
		JButton createButten = new JButton("\uBC29\uB9CC\uB4E4\uAE30");
		createButten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		createButten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		createButten.setFont(new Font("����", Font.PLAIN, 18));
		createButten.setBounds(466, 364, 171, 44);
		contentPane.add(createButten);

		// �����ϱ�
		JButton joinButten = new JButton("\uC785\uC7A5\uD558\uAE30");
		joinButten.setFont(new Font("����", Font.PLAIN, 18));
		joinButten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		joinButten.setBounds(466, 424, 171, 44);
		contentPane.add(joinButten);

		// �α׾ƿ�
		JButton logoutButten = new JButton("\uB85C\uADF8\uC544\uC6C3");
		logoutButten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		logoutButten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		logoutButten.setFont(new Font("����", Font.PLAIN, 18));
		logoutButten.setBounds(466, 483, 171, 44);
		contentPane.add(logoutButten);

		// ��˻���ư
		JButton chatSearchButton = new JButton("");
//		chatSearchButton.setIcon(new ImageIcon(MainViewFrame.class.getResource("/icon/search.png")));
		chatSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		chatSearchButton.setBounds(597, 298, 40, 40);
		contentPane.add(chatSearchButton);

		// ��˻� TEXT
		chatSearchText = new JTextField();
		chatSearchText.setText("\uBC29\uAC80\uC0C9");
		chatSearchText.setColumns(10);
		chatSearchText.setBounds(466, 298, 130, 40);
		contentPane.add(chatSearchText);

		// ����˻���ư
		JButton empSearchButton = new JButton("");
		empSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
//		empSearchButton.setIcon(new ImageIcon(MainViewFrame.class.getResource("/icon/search.png")));
		empSearchButton.setBounds(597, 27, 40, 40);
		contentPane.add(empSearchButton);

		// ����˻� TEXT
		empSearchText = new JTextField();
		empSearchText.setText("\uC0AC\uC6D0\uAC80\uC0C9");
		empSearchText.setBounds(467, 27, 130, 40);
		contentPane.add(empSearchText);
		empSearchText.setColumns(10);

		// ���� ���̺�
		String[] col1 = { "�μ�", "����", "�̸�", "��ȣ" };
		String[][] row1 = new String[0][3];

		model1 = new DefaultTableModel(row1, col1);
		table1 = new JTable(model1);
		JScrollPane js1 = new JScrollPane(table1);

		// ���� ���̺�
		String[] col2 = { "ä�ù��̸�", "�ο���" };
		String[][] row2 = new String[0][2];

		model2 = new DefaultTableModel(row2, col2);
		table2 = new JTable(model2);
		JScrollPane js2 = new JScrollPane(table2);

		getContentPane().setLayout(null);
		js1.setBounds(36, 71, 389, 29);
		getContentPane().add(js1);
		js2.setBounds(36, 340, 389, 29);
		getContentPane().add(js2);

		
		setVisible(true);

		
		
		Thread t1 = new Thread(this);
		t1.start();

		// ������ ����?----------------------------------- �����ߴ°Ű���.
//      String[] col3 = { "ä�ù��̸�" , "�ο���" };
//      ListModel<String> model3 = new DefaultListModel<>();
//      for(String s : col2){
//          ((DefaultListModel<String>)model3).addElement(s);
//      }
//      
//      jListChat = new JList<>(model3);
//      jListChat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//      jListChat.addListSelectionListener(this);
//      jListChat.setPreferredSize(new Dimension(389, 175));
//      jListChat.setLocation(38, 330);
//

//     
		// -----------------------------------------------------------------

		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {

			while (true) {

				if (call) {
					String resCom = (String) resMap.get("command");

					switch (resCom) {

					case "afterMain":

						List<EmpDTO> empList = (List<EmpDTO>) resMap.get("empList");
						List<ChatInfo> roomList = (ArrayList<ChatInfo>) resMap.get("roomList");
						String printChatList = "";

						String[][] row3 = new String[roomList.size()][2];
						if (roomList != null) {
							for (ChatInfo room : roomList) {

								ChatListDTO chat = room.getChatListDTO();

								String name = chat.getChatName(); // ä�ù� �̸�
								int i = room.getChatUserDTO().size(); // ä�ù� �ο� ��
								printChatList += name + "\t\t" + i + "��" + "\n";

								// chatListModel.
//							  
//							  
//							  String[] col3 = { name , i+"��"};
//							  
//							  
//							  JTable table2 = new JTable(model2); JScrollPane js2 = new
//							  JScrollPane(table2);

							}
						}

						// chatList.setText(printChatList);
						System.out.println(printChatList);
						if (empList != null) {
							for (EmpDTO emp : empList) {

							}
						}

						call = false;

						break;

					}

				}System.out.printf("");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}
}
