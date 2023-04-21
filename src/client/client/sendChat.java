package client.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class SendChat extends Thread {

	ObjectOutputStream oos;
	Socket s1;

	public SendChat(Socket s1) {
		// TODO Auto-generated constructor stub

		try {

			this.s1 = s1;
			oos = new ObjectOutputStream(s1.getOutputStream());

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
			

					
				HashMap<Object,Object> repMap = null;
				
				
				
				
				while(true) {

					
					

					String command ="";
					String window = null;
					
					
	
					
					
					
	
					
					
					oos.writeObject(repMap);
					oos.flush();
					
		
					}
					
		
				}
	
	
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
