package client.client;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import server.serverDTO.FileDTO;

public class FileDownGUIPlus extends JFrame implements Runnable {// implements Runnable

	// 여기에 스레드로 보낼때 필요한 변수들을 다넣고
	public static boolean call = false;
	private JPanel contentPane;
	private JTextField txt_name;
	private JTextField txt_path;
	private JTextArea fileListText;
	private DefaultListModel model;
	private JList list;
	private JScrollPane scrollPane;
	HashMap<Object, Object> reqMap; // 클라이언트가 보냄
	public static HashMap<Object, Object> resMap; // 클라이언트가 받음
	String client_path;
	String client_fname;
	String command;
	FileDTO fdto;
	ObjectOutputStream oos;
	Choice choice;
	String input;
	int chatId;
	List<FileDTO> files;

	/**
	 * Launch the application.
	 */

	// gui 창
	public FileDownGUIPlus(ObjectOutputStream oos, int chatId) throws IOException {
		this.oos = oos;
		this.chatId = chatId;

		String command = "findFileList";
		reqMap = new HashMap<Object, Object>();
		reqMap.put("command", command);
		reqMap.put("chatId", chatId);

		try {
			oos.writeObject(reqMap);
			oos.flush();
		} catch (Exception e) {

		}

		// 전체창
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400); // 전체 창 크기
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		Choice choice = new Choice();
		this.choice = choice;
		choice.setBounds(33, 28, 524, 40);
		contentPane.add(choice);

		JLabel lbl_name = new JLabel("\uD30C\uC77C\uC774\uB984");
		lbl_name.setFont(new Font("굴림", Font.PLAIN, 25));
		lbl_name.setForeground(new Color(0, 0, 0));
		lbl_name.setBounds(33, 160, 100, 40);
		contentPane.add(lbl_name);

		JLabel lbl_path = new JLabel("\uD30C\uC77C\uACBD\uB85C");
		lbl_path.setForeground(new Color(0, 0, 0));
		lbl_path.setFont(new Font("굴림", Font.PLAIN, 25));
		lbl_path.setBounds(33, 210, 100, 40);
		contentPane.add(lbl_path);

		txt_name = new JTextField();
		txt_name.setBounds(174, 164, 350, 40);
		contentPane.add(txt_name);
		txt_name.setColumns(10);

		txt_path = new JTextField();
		txt_path.setBounds(174, 214, 350, 40);
		contentPane.add(txt_path);
		txt_path.setColumns(10);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				setVisible(false);
			}
		});

		JButton btnNewButton = new JButton("\uB2E4\uC6B4\uB85C\uB4DC");
		btnNewButton.setBackground(new Color(240, 240, 240));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 30));
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				client_fname = txt_name.getText();
				client_path = txt_path.getText();
				fdto = files.get(choice.getSelectedIndex()); // 클라이언트가 선택한 file의 dto

				if (client_path == null || client_fname == null)
					JOptionPane.showMessageDialog(null, "파일경로와 이름을 입력해 주세요", "알림창", JOptionPane.ERROR_MESSAGE);
				else {
					File file = new File(client_path);
					File serf = new File(file, client_fname + ".txt");
					if (!file.exists())
						JOptionPane.showMessageDialog(null, "파일경로를 찾을 수 없습니다 \n 경로를 다시 확인해 주세요", "알림창",
								JOptionPane.ERROR_MESSAGE);
					else if (serf.exists()) {
						String[] options = { "YES", "NO" };
						int op = JOptionPane.showOptionDialog(null,
								"해당 파일은 존재하는 파일 입니다 \n 취소하시려면 NO \n 계속하시려면 YES를 눌려주세요", "알림창",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "NO");
						if (op == 0) {
							// 클라이언트가 고른 file dto를 multhread로 보냄
							reqMap = new HashMap<Object, Object>();
							String command = "downFile";
							reqMap.put("command", command);
							reqMap.put("fdto", fdto);
							reqMap.put("reFile", op);

							try {
								oos.writeObject(reqMap);
								oos.flush();

							} catch (IOException e1) {
							}
							JOptionPane.showMessageDialog(null, "다운로드가 완료되었습니다", "완료창",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							txt_name.setText("");
							txt_path.setText("");
						}
					} else {
						reqMap = new HashMap<Object, Object>();
						String command = "downFile";
						reqMap.put("command", command);
						reqMap.put("fdto", fdto);
						reqMap.put("reFile", 1);
					}
				}
				txt_name.setText("");
				txt_path.setText("");
			}
		});

		btnNewButton.setBounds(174, 293, 180, 60);
		contentPane.add(btnNewButton);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				setVisible(false);
			}
		});
		
		setVisible(true);
		
		Thread t1 = new Thread(this);
		t1.start();
	} // gui 창 end

	// 서버로 부터 온 정보를 받는 곳
	public void run() {

		while (true) {
			try {
				if (call) {
					String resCom = (String) resMap.get("command");

					switch (resCom) {

					case "downContent": // 서버가 보내온 파일의 내용을 받아서 클라이언트 pc에 작성
						String content = (String) resMap.get("content");
						int op = (Integer) resMap.get("reFile");
						PrintWriter pw = null;
						File file = new File(client_path);
						if (op == 1) {
							try {
								File file2 = new File(file, client_fname + ".txt");
								if (!file2.exists())
									file2.createNewFile();
								FileWriter fw = new FileWriter(file2);
								pw = new PrintWriter(fw, true); // 덮어쓰지 못한다
								pw.print(content);
								pw.close();
							} finally {
							}
						} else {
							try {
								File file2 = new File(file, "re_" + client_fname + ".txt");
								if (!file2.exists())
									file2.createNewFile();
								FileWriter fw = new FileWriter(file2);
								pw = new PrintWriter(fw, true); // 덮어쓰지 못한다
								pw.print(content);
								pw.close();
							} finally {
							}
						}
						call = false;
						break;

					case "afterFindFileList": // Rcv로부터 fileList를 받아온다

						List<FileDTO> fileList = (List<FileDTO>) resMap.get("fileList");


						if (fileList.size() > 0) {
							this.files = fileList;
							for (int i = 0; i < files.size(); i++) {
								choice.add(files.get(i).getFileName());
							}
						}

						call = false;
						break;

					}
				}System.out.print("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
