package client.client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import server.serverDTO.ChatInfo;
import server.serverDTO.ChatListDTO;
import server.serverDTO.ChatUserDTO;
import server.serverDTO.EmpDTO;

public class ChatGUI extends JFrame implements Runnable, ActionListener {
/*
 * 


�����ϱ� event

chatList.add(new chatGUI(oos, ChatInfo, emp));


-������
ChatInfo ��ü�� ���̸�, ������ ���
������ ä�ó��� ��û (ChatInfo,EmpId)
�������� chatPath�� �̿��ؼ� ������ ã�� client�� ����(����,chatId)

rcvTread���� �ް�, static chatList���� chatId�� ���� �����ִ� ä�ù� ��ü�� ã�Ƽ� textarea ���
���� �����ִ� ä�ù� ��ü�� ���ٸ� �������� ����â�� ä�ù� ��Ͽ��� �ش� ���� ������ ����




-���� �̺�Ʈ
�Է�â�� String�� chatId,empId ������ ����
�������� ä�ù� ���Ͽ� String�� ������Ʈ�ϰ� 
findChatThread�޼ҵ�� ä�ù濡 ���ִ� ����ڸ� ��� ã�� send �޼ҵ� ����




-���� �ٿ� �̺�Ʈ
���� �ٿ�â ȣ��


 */
	private InputStream inputStream;
	private OutputStream outputStream;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;
	
	private boolean call = false;
	private HashMap<Object, Object> resMap = null;
	ObjectOutputStream oos;
	ChatInfo room;
	EmpDTO myEmp;
	
	
	//â
	private JPanel contentPane;
	
	//ä��
	private JLabel chatName; //���̸�
	private JButton fileSendBT; //���� ���ε�
	private JTextArea chatInput; // Text ���
	private JButton chatSendBT; //�Է¹��� text ������ ��ư
	private JTextArea chatOutput; //�Է�
	//������
	private JLabel userLabel; 
	private JTextArea userList;
	private JButton firedownBT;
	
	
	private JScrollPane scrollPane;
	private JScrollPane OutputScrollPane;
	private JScrollPane InputScrollPane;
	private HashMap<Object, Object> reqMap;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ChatGUI(ObjectOutputStream oos, ChatInfo room, EmpDTO emp) { //main gui
		
		this.oos = oos;
		this.room = room;
		this.myEmp =emp;
		
		reqMap = new HashMap<Object, Object>();
		String names = "";
		
		
		String roomName = room.getChatListDTO().getChatName();
		
		for(ChatUserDTO user :room.getChatUserDTO()){
			
			names += user.getName() + "\n";
			
		}
		
		//// ������ ��û
		
		
		reqMap.put("command","readChat");
		reqMap.put("room", room);
		
		try {
			
			oos.writeObject(reqMap);
			oos.flush();
			
		}catch(Exception e){
			
			
		}
		
		
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 591);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		chatSendBT = new JButton("");
		chatSendBT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}
		});
		chatSendBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		chatSendBT.setIcon(new ImageIcon(ChatGUI.class.getResource("/icon/chatbt.png")));
		chatSendBT.setBounds(535, 468, 97, 83);
		contentPane.add(chatSendBT);
		
		
		fileSendBT = new JButton("");
		fileSendBT.setIcon(new ImageIcon(ChatGUI.class.getResource("/icon/plus.png")));
		fileSendBT.setBounds(12, 468, 119, 83);
		contentPane.add(fileSendBT);
		
		
		
		chatInput = new JTextArea();
		chatInput.setFont(new Font("����", Font.PLAIN, 12));
		chatInput.setBounds(143, 56, 487, 402);
		chatInput.setColumns(10);
		chatInput.setEditable(false);
		
		
		JScrollBar InputScrollBar = new JScrollBar(JScrollBar.VERTICAL);

		
		InputScrollPane = new JScrollPane(chatInput);
		InputScrollPane.setBounds(143, 56, 487, 402);
		InputScrollPane.setViewportView(chatInput);
		InputScrollPane.setVerticalScrollBar(InputScrollBar);
		contentPane.add(InputScrollPane);
		
		

		
		

		
		
	

	
		OutputScrollPane = new JScrollPane();
		OutputScrollPane.setBounds(143, 468, 393, 83);
		contentPane.add(OutputScrollPane);
	
		JTextArea chatOutput = new JTextArea();
		OutputScrollPane.setViewportView(chatOutput);
		JScrollBar OutputScrollBar = new JScrollBar(JScrollBar.VERTICAL);
		OutputScrollPane.setVerticalScrollBar(OutputScrollBar);
		
		chatOutput = new JTextArea();
		chatOutput.setBounds(143, 468, 393, 83);

	
		
		chatName = new JLabel("\uBC29\uC774\uB984");
		chatName.setFont(new Font("����", Font.PLAIN, 20));
		chatName.setBounds(12, 10, 643, 37);
		contentPane.add(chatName);
		
		userLabel = new JLabel(" \uCC44\uD305 \uCC38\uC5EC\uC790");
		userLabel.setFont(new Font("����", Font.PLAIN, 20));
		userLabel.setBounds(12, 50, 119, 32);
		contentPane.add(userLabel);
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 92, 119, 282);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(userList);
		JScrollBar scrollBar2 = new JScrollBar(JScrollBar.VERTICAL);
		scrollPane.setVerticalScrollBar(scrollBar2);
		
		
		userList = new JTextArea();
		chatInput.setFont(new Font("����", Font.PLAIN, 12));
		userList.setBounds(12, 92, 119, 282);
		contentPane.add(userList);
		
		

		
		
		
		firedownBT = new JButton("\uD30C\uC77C\uB2E4\uC6B4");
		firedownBT.setFont(new Font("����", Font.PLAIN, 20));
		firedownBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		firedownBT.setBounds(12, 390, 119, 68);
		contentPane.add(firedownBT);
		
		setVisible(true);
		
		Thread t1 = new Thread(this);
		t1.start();
		
		
		
	}
	
	public void SetRoom(String roomName, String users, String chat) {
		
		chatName.setText(roomName);
		userList.setText(users);
		
		
		chatInput.setText(chat);
		
		
		
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

	public boolean isCall() {
		return call;
	}

	public void setCall(boolean call) {
		this.call = call;
	}

	public HashMap<Object, Object> getResMap() {
		return resMap;
	}

	public void setResMap(HashMap<Object, Object> resMap) {
		this.resMap = resMap;
	}
}
