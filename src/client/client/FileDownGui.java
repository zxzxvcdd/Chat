package client.client;

import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import server.serverDAO.ServerDAO;
import server.serverDTO.FileDTO;
import server.serverService.ChatService;
import java.awt.Color;
import java.awt.Font;

public class FileDownGui extends JFrame implements Runnable {

	// 여기에 스레드로 보낼때 필요한 변수들을 다넣고
	static boolean call = false;
	private JPanel contentPane;
	private JTextField txt_name;
	private JTextField txt_path;
	HashMap<Object, Object> reqMap; // 클라이언트가 보냄
	static HashMap<Object, Object> resMap; // 클라이언트가 받음
	
	String client_path;
	String client_fname;
	String command;
	FileDTO fdto;
	ObjectOutputStream oos;
	List<FileDTO> files;
	

	/**
	 * Launch the application.
	 */	
	
	
	// gui 창
	public FileDownGui(ObjectOutputStream oos, int chatId) throws IOException {
		this.oos = oos;
		
		// 전체창
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      setBounds(100, 100, 600, 600); // 전체 창 크기
	      contentPane = new JPanel();
	      contentPane.setBackground(new Color(255, 238, 222));
	      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	      setContentPane(contentPane);
	      contentPane.setLayout(null);
	      
	      JLabel lblNewLabel = new JLabel("\uD30C\uC77C \uB2E4\uC6B4\uB85C\uB4DC \uCC3D");
	      lblNewLabel.setForeground(new Color(100, 50, 0));
	      lblNewLabel.setFont(new Font("굴림", Font.BOLD, 40));
	      lblNewLabel.setBounds(125, 10, 350, 60);
	      contentPane.add(lblNewLabel);

	      JLabel lbl_name = new JLabel("\uD30C\uC77C\uC774\uB984");
	      lbl_name.setFont(new Font("굴림", Font.PLAIN, 25));
	      lbl_name.setForeground(new Color(100, 50, 0));
	      lbl_name.setBounds(50, 250, 100, 40);
	      contentPane.add(lbl_name);

	      JLabel lbl_path = new JLabel("\uD30C\uC77C\uACBD\uB85C");
	      lbl_path.setForeground(new Color(100, 50, 0));
	      lbl_path.setFont(new Font("굴림", Font.PLAIN, 25));
	      lbl_path.setBounds(50, 350, 100, 40);
	      contentPane.add(lbl_path);
	      
	      txt_name = new JTextField();
	      txt_name.setBounds(200, 250, 350, 40);
	      contentPane.add(txt_name);
	      txt_name.setColumns(10);
	      
	      txt_path = new JTextField();
	      txt_path.setBounds(200, 350, 350, 40);
	      contentPane.add(txt_path);
	      txt_path.setColumns(10);
	      
	   // fileList를 서버에게 요청한다
	      String command = "findFileList";
	      reqMap = new HashMap<Object, Object>();
	      reqMap.put("command", command);
	      reqMap.put("chatId", chatId);
	      oos.writeObject(reqMap);
	      oos.flush();
			
	    // fileList를 보여줌
//	      Choice choice = new Choice();
//
//	      for(int i=0; i<=files.size(); i++) {	
//	    	  choice.add(files.get(i).getFileName());
//	      }
//	      choice.setBounds(50, 110, 500, 40);
//	      contentPane.add(choice);
	      
	      
	      addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
					setVisible(false);
				}
			});
	      
	      JButton btnNewButton = new JButton("\uB2E4\uC6B4\uB85C\uB4DC");
	      btnNewButton.setBackground(new Color(255, 130, 10));
	      btnNewButton.setForeground(new Color(255, 255, 255));
	      btnNewButton.setFont(new Font("굴림", Font.PLAIN, 30));
	      btnNewButton.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent e) {
	            client_fname = txt_name.getText();
	            client_path = txt_path.getText();
//	            fdto = files.get(choice.getSelectedIndex()); // 클라이언트가 선택한 file의 dto
	            
	            // 클라이언트가 고른 file dto를 multhread로 보냄
	            reqMap = new HashMap<Object, Object>();
				String command = "downFile";
				reqMap.put("command",command);				
				reqMap.put("fdto",fdto);
				
				try {
					oos.writeObject(reqMap);
					oos.flush();
					
				} catch (IOException e1) {}	
				txt_name.setText("");
				txt_path.setText("");
	         }
	      });
	      
	      btnNewButton.setBounds(210, 450, 180, 60);
	      contentPane.add(btnNewButton);
	      
	      
	      
	      addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
					setVisible(false);
				}
			});
	      Thread t1 = new Thread(this);
			t1.start();
	 } // gui 창 end
	
	// 서버로 부터 온 정보를 받는 곳
	public void run() {
		try {
			while (true) {
				if (call) {
					String resCom = (String) resMap.get("command");
					
					switch (resCom) {
					
					case "downContent": // 서버가 보내온 파일의 내용을 받아서 클라이언트 pc에 작성
						String content = (String) resMap.get("content");
						String path = client_path + "/" + client_fname +".txt";
						PrintWriter pw = null;
						try {
							if (path != null) {
								File file = new File(path);
								if(!file.exists()) {
									JOptionPane.showMessageDialog(null, "파일경로를 찾을 수 없습니다 \n 경로를 다시 확인해 주세요", "알림창", JOptionPane.ERROR_MESSAGE);
									break;
								}							
								BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
								pw = new PrintWriter(bw, true); // 파일 생성
							}
							pw.print(content);
							pw.close();
							JOptionPane.showMessageDialog(null, "다운로드가 완료되었습니다", "완료창", JOptionPane.INFORMATION_MESSAGE);
						} finally {}
						call=false;
						break;
						
					case "afterFindFileList": // Rcv로부터 fileList를 받아온다
						
						System.out.println("Frame after Find 호출");
						files = new ArrayList<FileDTO>();
						files = (List<FileDTO>)resMap.get("fileList");
						if(files != null) System.out.println("ok");
						else System.out.println("no");
						call=false;
						break;
					}
				}else System.out.printf("");
			}
		} catch (Exception e) {}
	}
}
