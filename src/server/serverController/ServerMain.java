package server.serverController;

import java.io.IOException;

public class ServerMain {

	
	public static void main(String[] args) throws NumberFormatException, IOException {
	
		
		
//		if(args.length !=1) {
//			
//		System.out.println("포트번호를 입력");
//		}

//		ServerController server = new ServerController(Integer.parseInt(args[0]));
		ServerController server = new ServerController(1112);
		
	}
	
	
	
}
