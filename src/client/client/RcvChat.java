package client.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;

public class RcvChat extends Thread {

	ObjectInputStream ois;
	Socket s1;
	
	static HashMap<Object,Object> resMap;

	public RcvChat(Socket s1) {
		// TODO Auto-generated constructor stub

		try {
			
			this.s1 = s1;
			ois = new ObjectInputStream(s1.getInputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		
		
		try {
			
			
			
			while(true) {


				
				try {
					resMap = (HashMap<Object,Object>)ois.readObject();
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				String command = (String)resMap.get("command");
				
				switch(command) {
				
				case "empList" :
					
					
					break;
					
				case "LoginSucces":
					
					
					
					break;

				}
				
				
				
				
			}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
		
	}

}
