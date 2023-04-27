package client.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

public class ChatClient {
	
	
	public static List<ChatGUI> chatList = new ArrayList<ChatGUI>();


    
	public static void main(String[] args) {
	
		ObjectInputStream ois;
		ObjectOutputStream oos;
		
		Socket s1;
		

		
		
		try {
			
			
			
			s1 = new Socket("127.0.0.1", 1115);
			System.out.println(s1.getInetAddress().getHostAddress() + "연결 중");

			
			

			ois = new ObjectInputStream(s1.getInputStream());
			oos = new ObjectOutputStream(s1.getOutputStream());
			
			RcvThread rcv = new RcvThread(ois);
			
			rcv.start();
			

		
			new Login(oos);
			

			
			
			
			

			
		
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 
		
		
		
		
	}
	
}

