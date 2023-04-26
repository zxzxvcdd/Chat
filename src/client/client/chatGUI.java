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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import server.serverDTO.ChatInfo;
import server.serverDTO.ChatListDTO;
import server.serverDTO.ChatUserDTO;
import server.serverDTO.EmpDTO;

public class ChatGUI extends JFrame implements Runnable, ActionListener {
	/*
	 * 
	 * 
	 * 
	 * 입장하기 event
	 * 
	 * chatList.add(new chatGUI(oos, ChatInfo, emp));
	 * 
	 * 
	 * -생성자 ChatInfo 객체로 방이름, 참여자 출력 서버로 채팅내용 요청 (ChatInfo,EmpId) 서버에서 chatPath를
	 * 이용해서 파일을 찾아 client로 전송(파일,chatId)
	 * 
	 * rcvTread에서 받고, static chatList에서 chatId로 현재 열려있는 채팅방 객체를 찾아서 textarea 출력 만약
	 * 열려있는 채팅방 객체가 없다면 비프음과 메인창의 채팅방 목록에서 해당 열의 색깔을 변경
	 * 
	 * 
	 * 
	 * 
	 * -전송 이벤트 입력창의 String과 chatId,empId 서버로 전달 서버에서 채팅방 파일에 String을 업데이트하고
	 * findChatThread메소드로 채팅방에 들어가있는 사용자를 모두 찾아 send 메소드 실행
	 * 
	 * 
	 * 
	 * 
	 * -파일 다운 이벤트 파일 다운창 호출
	 * 
	 * 
	 */
	private InputStream inputStream;
	private OutputStream outputStream;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;

	private boolean call = false;
	private HashMap<Object, Object> resMap = null;
	ObjectOutputStream oos;
	public ChatInfo room;
	public EmpDTO myEmp;

	// 창
	private JPanel contentPane;

	// 채팅
	private JTextArea chatName; // 방이름
	private JButton fileSendBT; // 파일 업로드
	private JTextArea chatInput; // Text 출력
	private JButton chatSendBT; // 입력받은 text 보내는 버튼
	private JTextArea chatOutput; // 입력
	// 접속자
	private JLabel userLabel;
	private JTextArea userList;
	private JButton fileDownBT;

	
	
	private String[] empTabNames = { "id","이름" };
	private DefaultTableModel empModel = new DefaultTableModel(empTabNames, 0);
	private JTable empTable;
	
	
	private JScrollPane userScrollPane;
	private JScrollPane outputScrollPane;
	private JScrollPane inputScrollPane;
	private HashMap<Object, Object> reqMap;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ChatGUI(ObjectOutputStream oos, ChatInfo room, EmpDTO emp) { // main gui

		this.oos = oos;
		this.room = room;
		this.myEmp = emp;
		


		reqMap = new HashMap<Object, Object>();
		String names = "";

		String roomName ="";
		roomName = room.getChatListDTO().getChatName();

		setTitle(roomName);
		
		
		for (ChatUserDTO user : room.getChatUserDTO()) {

			names += user.getName() + "\n";

		}

		//
		for (ChatUserDTO user : room.getChatUserDTO()) {

			empModel.addRow(new Object[] { user.getEmployeeId(), user.getName()});

		}

		
		
		//// 생성시 요청

		reqMap.put("command", "readChat");
		reqMap.put("room", room);

		try {

			oos.writeObject(reqMap);
			oos.flush();

		} catch (Exception e) {

			System.out.println("read error");

		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 591);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		chatSendBT = new JButton("\uC804\uC1A1");
		chatSendBT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				reqMap.put("command", "sendChat");
				reqMap.put("room", room);
				String sendChat = chatOutput.getText();
				reqMap.put("chat", sendChat);
 
				try {

					oos.writeObject(reqMap);
					oos.flush();

				} catch (Exception e2) {

					System.out.println("send error");
				}

			}
		});
		chatSendBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		chatSendBT.setIcon(new ImageIcon(ChatGUI.class.getResource("/client/icon/chatbt.png")));
		chatSendBT.setBounds(535, 468, 97, 83);
		contentPane.add(chatSendBT);

		fileSendBT = new JButton("\uD30C\uC77C\uC5C5\uB85C\uB4DC");
		fileSendBT.setFont(new Font("굴림", Font.PLAIN, 16));
		fileSendBT.setIcon(new ImageIcon(ChatGUI.class.getResource("/client/icon/plus.png")));
		fileSendBT.setBounds(12, 468, 119, 83);
		contentPane.add(fileSendBT);

		chatInput = new JTextArea();
		chatInput.setFont(new Font("굴림", Font.PLAIN, 12));
		chatInput.setBounds(143, 56, 487, 402);
		chatInput.setColumns(10);
		chatInput.setEditable(false);


		inputScrollPane = new JScrollPane(chatInput);
		inputScrollPane.setBounds(143, 56, 487, 402);
		inputScrollPane.setViewportView(chatInput);
		contentPane.add(inputScrollPane);

		outputScrollPane = new JScrollPane();
		outputScrollPane.setBounds(143, 468, 393, 83);
		contentPane.add(outputScrollPane);

		JTextArea chatOutput = new JTextArea();
		outputScrollPane.setViewportView(chatOutput);
		

		chatOutput = new JTextArea();
		chatOutput.setBounds(143, 468, 393, 83);



		userLabel = new JLabel(" \uCC44\uD305 \uCC38\uC5EC\uC790");
		userLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		userLabel.setBounds(12, 50, 119, 32);
		contentPane.add(userLabel);

		
	

		empTable = new JTable(empModel);
		empTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {


				}
			});

		empTable.getColumn("id").setWidth(0);
		empTable.getColumn("id").setMaxWidth(0);
		empTable.getColumn("id").setMinWidth(0);
		empTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		userScrollPane= new JScrollPane(empTable);
		userScrollPane.setBounds(12, 92, 119, 282);
		contentPane.add(userScrollPane);
	

		fileDownBT = new JButton("\uD30C\uC77C\uB2E4\uC6B4");
		fileDownBT.setFont(new Font("굴림", Font.PLAIN, 16));
		fileDownBT.setBounds(12, 390, 119, 68);
		contentPane.add(fileDownBT);
		


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

					case "afterReadChat":

						chatInput.setText((String) resMap.get("chat"));

						int kkeut = chatInput.getText().length();
						chatInput.setCaretPosition(kkeut);

						call = false;

						break;

					case "afterSendChat":

						JOptionPane.showMessageDialog(null, "전송실패", "Message", JOptionPane.ERROR_MESSAGE);

						call = false;

						break;

					case "afterInvite":

						JOptionPane.showMessageDialog(null, "초대실패", "Message", JOptionPane.ERROR_MESSAGE);

						
						call = false;

						break;

					case "send":

						chatInput.setText((String) resMap.get("chat"));

						call = false;

						break;

					case "updateRoom":

						room = (ChatInfo) resMap.get("roomList"); 

						call = false;

						
						break;

					}

				}
				System.out.printf("");
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

	public ChatInfo getRoom() {
		return room;
	}

	public EmpDTO getMyEmp() {
		return myEmp;
	}
}
