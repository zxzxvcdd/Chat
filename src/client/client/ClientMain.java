package client.client;

public class ClientMain {

	
	public static void main(String[] args) {
		
		
		String ip = "127.0.0.1";
		int portNo = 3333;
		ChatClient client = new ChatClient(ip,portNo);
		
	}
}
