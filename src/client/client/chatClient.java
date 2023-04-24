package client.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatClient {


    
	public static void main(String[] args) {
	
		Socket s1;
		ObjectInputStream ois;
		ObjectOutputStream oos;
//		static List<ChatRoomFrame> chatList;
		
		
		try {
			
			
			
			s1 = new Socket("127.0.0.1", 4452);
			System.out.println(s1.getInetAddress().getHostAddress() + " ¿¬°áµÊ");

			
			

			ois = new ObjectInputStream(s1.getInputStream());
			oos = new ObjectOutputStream(s1.getOutputStream());
			
			RcvThread rcv = new RcvThread(ois);
			
			rcv.start();
			
			new ClientTestFrame(oos);
			
			
			
			
			

			
		
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 
		
		
		
		
	}
	
	public ChatClient(String ip, int port) {
		
//		try {
//			
//			
//			
//			s1 = new Socket(ip, port);
//			System.out.println(s1.getInetAddress().getHostAddress() + " ¿¬°áµÊ");
//
//
//			ois = new ObjectInputStream(s1.getInputStream());
//			oos = new ObjectOutputStream(s1.getOutputStream());
//			
//			new ClientMainFrame(ois,oos) {
//		          public void closeWork() throws IOException {
//			           oos.close();
//			           ois.close();
//			           System.exit(0);
//		       }
//			};
//			
//			
//			
//			
//			
//
//			
//		
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//		} finally {
//			// ¼ÒÄÏ ´Ý±â (¿¬°á ²÷±â)
//			try {
//				if (s1 != null) {
//					s1.close();
//				}
//				if (oos != null) {
//					oos.close();
//				}
//				if (ois != null) {
//					ois.close();
//				}
//			} catch (IOException e) {
//				System.out.println(e.getMessage());
//			}
//		}
	}
}
