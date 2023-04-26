package client.client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.serverService.ChatService;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// 
public class FileUpGui extends JFrame implements Runnable {


	static boolean call = false;
	private JPanel contentPane;
	private JTextField txt_name;
	private JTextField txt_path;
	HashMap<Object, Object> reqMap; // 클라이언트가 보냄
	static HashMap<Object, Object> resMap; // 클라이언트가 받음
	String file_name;
	String file_path;
	String client_path;
	String client_fname;
	ObjectOutputStream oos;

	/**
	 * Launch the application.
	 */
		
	public FileUpGui(ObjectOutputStream oos, int employeeId, int chatId) throws Exception { 
		this.oos = oos;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      setBounds(100, 100, 600, 400); // 전체 창 크기
	      contentPane = new JPanel();
	      contentPane.setBackground(new Color(255, 238, 222));
	      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	      setContentPane(contentPane);
	      contentPane.setLayout(null);
	      
	      JLabel lblNewLabel = new JLabel("\uD30C\uC77C \uC5C5\uB85C\uB4DC \uCC3D");
	      lblNewLabel.setForeground(new Color(100, 50, 0));
	      lblNewLabel.setFont(new Font("굴림", Font.BOLD, 41));
	      lblNewLabel.setBounds(150, 10, 285, 60);
	      contentPane.add(lblNewLabel);

	      JLabel lbl_name = new JLabel("\uD30C\uC77C\uC774\uB984");
	      lbl_name.setForeground(new Color(100, 50, 0));
	      lbl_name.setFont(new Font("굴림", Font.PLAIN, 25));
	      lbl_name.setBounds(50, 100, 100, 40);
	      contentPane.add(lbl_name);

	      JLabel lbl_path = new JLabel("\uD30C\uC77C\uACBD\uB85C");
	      lbl_path.setForeground(new Color(100, 50, 0));
	      lbl_path.setFont(new Font("굴림", Font.PLAIN, 25));
	      lbl_path.setBounds(50, 200, 100, 40);
	      contentPane.add(lbl_path);
	      
	      txt_name = new JTextField();
	      txt_name.setBounds(200, 100, 350, 40);
	      contentPane.add(txt_name);
	      txt_name.setColumns(10);
	      
	      txt_path = new JTextField();
	      txt_path.setBounds(200, 200, 350, 40);
	      contentPane.add(txt_path);
	      txt_path.setColumns(10);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				setVisible(false);		
			}
		});
		
		JButton btn_upload = new JButton("\uC5C5\uB85C\uB4DC");
		btn_upload.setBackground(new Color(255, 130, 10));
		btn_upload.setForeground(new Color(255, 255, 255));
		btn_upload.setFont(new Font("굴림", Font.PLAIN, 30));
		btn_upload.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				client_fname = txt_name.getText();
	            client_path = txt_path.getText();
				String path = client_path + "/" + client_fname +".txt";
				File file = new File(path);
				String content = "";
				if(!file.exists()) JOptionPane.showMessageDialog(null, "파일경로를 찾을 수 없습니다 \n 경로를 다시 확인해 주세요", "알림창", JOptionPane.ERROR_MESSAGE);
				FileReader fr;
				try {
					fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
					String line = "";
					
					while((line = br.readLine()) != null)	content += (line +"\n");
					br.close();
				} catch (FileNotFoundException e2) {} 
				  catch (IOException e1) {}			
				reqMap = new HashMap<Object, Object>();
				String command = "saveFile";
				reqMap.put("command",command);
				reqMap.put("fileName",client_fname);
				reqMap.put("chatId",chatId);
				reqMap.put("employeeId",employeeId);
				reqMap.put("content",content);
				
				try {
					oos.writeObject(reqMap);
					oos.flush();
					
				} catch (IOException e1) {}	
								
				txt_name.setText("");
				txt_path.setText("");
			}
		});
		btn_upload.setBounds(200, 270, 180, 60);
		contentPane.add(btn_upload);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				setVisible(false);
			}
		});
      Thread t1 = new Thread(this);
		t1.start();
	}
	
	public void run() {
		try {
			while (true) {
				if (call) {
					String resCom = (String) resMap.get("command");
					
					switch (resCom) {
					
					case "alram" :
						boolean alarm = (boolean)resMap.get("alarm");
						if(alarm)	JOptionPane.showMessageDialog(null, "파일 업로드에 실패하였습니다", "알림창", JOptionPane.ERROR_MESSAGE);			
						call=false;
						break;
						
					case "writeFilePath":
						boolean writeFilePath = (boolean)resMap.get("writeFilePath");
						if(!writeFilePath) JOptionPane.showMessageDialog(null, "파일 업로드에 실패하였습니다", "알림창", JOptionPane.ERROR_MESSAGE);
						else	JOptionPane.showMessageDialog(null, "다운로드가 완료되었습니다", "완료창", JOptionPane.INFORMATION_MESSAGE);
						break;
					}
				}System.out.print("");
			}
		} finally {}
	}
}
