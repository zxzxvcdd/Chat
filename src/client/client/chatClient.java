package client.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatClient {

	private Socket s1;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
    
	
	public ChatClient(String ip, int port) {
		
		try {
			
			
			
			s1 = new Socket(ip, port);
			System.out.println(s1.getInetAddress().getHostAddress() + " ¿¬°áµÊ");


			ois = new ObjectInputStream(s1.getInputStream());
			oos = new ObjectOutputStream(s1.getOutputStream());
			
			
			
			
			
			

			
		
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			// ¼ÒÄÏ ´Ý±â (¿¬°á ²÷±â)
			try {
				if (s1 != null) {
					s1.close();
				}
				if (oos != null) {
					oos.close();
				}
				if (ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
