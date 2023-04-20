package server.serverService;

import java.io.IOException;

public class ServerMain {

	
	public static void main(String[] args) throws NumberFormatException, IOException {
	
		
		
		if(args.length !=2) {
			
		System.out.println("포트번호 두개를 입력");
		}

		ServerController server = new ServerController(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		
	}
	
	
	
}
