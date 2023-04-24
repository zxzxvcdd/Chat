package client.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.awt.event.WindowAdapter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class ClientTestFrame extends JFrame implements Runnable, ActionListener {

	 JFrame frame;
	
	 ObjectInputStream ois;
	 ObjectOutputStream oos;
	 JTextField textField;
	 HashMap<Object, Object> reqMap;
	 HashMap<Object, Object> resMap;
	String command;
	JLabel lblNewLabel_1;


	/**
	 * Create the application.
	 */
	public ClientTestFrame(ObjectInputStream ois, ObjectOutputStream oos) {
		
		this.ois = ois;
		this.oos = oos;
		


		setSize(800, 800); 
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(101, 122, 116, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(131, 502, 97, 23);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("\uACB0\uACFC");
		lblNewLabel_1.setBounds(160, 249, 193, 58);
		getContentPane().add(lblNewLabel_1);
		setVisible(true);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				System.out.println("클릭이벤트");
				
				reqMap = new HashMap<Object, Object>();
				command = "join";
				
				reqMap.put("command",command);
				
				reqMap.put("name",textField.getText());
				
				System.out.println(reqMap.get("name"));
				
				try {
					oos.writeObject(reqMap);
					oos.flush();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
				
				
			}
		});
		

		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0); 
				setVisible(false);
			}
		});
		
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
				 
				 
				 resMap = (HashMap<Object, Object>)ois.readObject();
				 
				 String resCom = (String)resMap.get("command");
				 
				 System.out.println("서버응답 "+command);
				 switch(resCom) {
				 
				 
				 case "afterJoin" :
					 
					 String result = (String)resMap.get("afterJoin");
					 System.out.println("결과"+result);
					 lblNewLabel_1.setText(result);
					 
					 
					 break;
				 
				 
				 
				 }
				 
				 
			 }
				 
				 
			 }catch (Exception e) {
				 
				 
			 }
		
	}
}
