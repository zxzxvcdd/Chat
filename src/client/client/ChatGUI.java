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
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import server.serverDTO.ChatInfo;
import server.serverDTO.ChatUserDTO;
import server.serverDTO.EmpDTO;

public class ChatGUI extends JFrame implements Runnable, ActionListener {

	private InputStream inputStream;
	private OutputStream outputStream;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;

	private boolean call = false;
	private HashMap<Object, Object> resMap = null;
	ObjectOutputStream oos;
	public ChatInfo room;
	public EmpDTO myEmp;

	// â
	private JPanel contentPane;

	// ä��
	private JTextArea chatName; // ���̸�
	private JButton fileSendBT; // ���� ���ε�
	private JTextArea chatInput; // Text ���
	private JButton chatSendBT; // �Է¹��� text ������ ��ư
	private JTextField chatOutput; // �Է�
	// ������
	private JLabel userLabel;
	private JTextArea userList;
	private JButton fileDownBT;

	private String[] empTabNames = { "사번", "이름" };
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

		ChatClient.chatList.add(this);

		reqMap = new HashMap<Object, Object>();
		String names = "";

		String roomName = "";
		roomName = room.getChatListDTO().getChatName();

		setTitle(roomName);

		for (ChatUserDTO user : room.getChatUserDTO()) {

			names += user.getName() + "\n";

		}

		//
		for (ChatUserDTO user : room.getChatUserDTO()) {

			empModel.addRow(new Object[] { user.getEmployeeId(), user.getName() });

		}

		//// ������ ��û

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

				reqMap = new HashMap<Object, Object>();
				reqMap.put("command", "sendChat");
				reqMap.put("room", room);

				String sendChat = chatOutput.getText();
				System.out.println(sendChat);
				reqMap.put("chat", sendChat);
				chatOutput.setText("");

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

		chatInput = new JTextArea();
		chatInput.setFont(new Font("����", Font.PLAIN, 12));
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

		chatOutput = new JTextField();
		chatOutput.setBounds(143, 468, 393, 83);
		outputScrollPane.setViewportView(chatOutput);

		userLabel = new JLabel(" \uCC44\uD305 \uCC38\uC5EC\uC790");
		userLabel.setFont(new Font("����", Font.PLAIN, 20));
		userLabel.setBounds(12, 50, 119, 32);
		contentPane.add(userLabel);

		empTable = new JTable(empModel);
		empTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		empTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		userScrollPane = new JScrollPane(empTable);
		userScrollPane.setBounds(12, 92, 119, 282);
		contentPane.add(userScrollPane);

		fileSendBT = new JButton("");
		fileSendBT.setFont(new Font("����", Font.PLAIN, 16));
		fileSendBT.setIcon(new ImageIcon(ChatGUI.class.getResource("/client/icon/plus.png")));
		fileSendBT.setBounds(12, 468, 119, 83);
		contentPane.add(fileSendBT);

		fileSendBT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {


				try {
					new FileUpGUI(oos, emp.getEmployeeId(), room.getChatListDTO().getChatId()).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});

		fileDownBT = new JButton("\uD30C\uC77C\uB2E4\uC6B4");
		fileDownBT.setFont(new Font("����", Font.PLAIN, 16));
		fileDownBT.setBounds(12, 390, 119, 68);
		contentPane.add(fileDownBT);

		fileDownBT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				
				try {
					new FileDownGUIPlus(oos, room.getChatListDTO().getChatId()).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		setVisible(true);

		Thread t1 = new Thread(this);
		t1.start();

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

						String firstChat = (String) resMap.get("chat");

						chatInput.setText(firstChat);

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
