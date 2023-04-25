package client.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.clientDTO.EmpDTO;

public class ChatClient {
	
//	static List<ChatRoomFrame> chatList;


    
	public static void main(String[] args) {
	
		Socket s1;
		ObjectInputStream ois;
		ObjectOutputStream oos;

		
		
		try {
			
			
			
			s1 = new Socket("127.0.0.1", 1116);
			System.out.println(s1.getInetAddress().getHostAddress() + " ¿¬°áµÊ");

			
			

			ois = new ObjectInputStream(s1.getInputStream());
			oos = new ObjectOutputStream(s1.getOutputStream());
			
			RcvThread rcv = new RcvThread(ois);
			
			rcv.start();
			
//			new ClientTestFrame(oos);
			
			EmpDTO emp = new EmpDTO();
			
			new EmployeeSearch(oos, emp);
			
			
			
			
			
			

			
		
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 
		
		
		
		
	}
	
}

