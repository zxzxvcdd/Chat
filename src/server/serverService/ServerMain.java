package server.serverService;

import java.io.IOException;

public class ServerMain {

	
	public static void main(String[] args) throws NumberFormatException, IOException {
	
		
		
		if(args.length !=2) {
			
		System.out.println("��Ʈ��ȣ �ΰ��� �Է�");
		}

		ServerController server = new ServerController(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		
	}
	
	
	
}
