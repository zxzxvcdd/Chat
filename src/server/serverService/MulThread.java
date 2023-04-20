package server.serverService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import server.serverDTO.EmpDTO;

public class MulThread extends Thread {
	
	Socket socket;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	ObjectInputStream ois = new ObjectInputStream(is);
	ObjectOutputStream oos = new ObjectOutputStream(os);
	
	EmpDTO emp;
	 
	
	
	public MulThread(Socket s1) throws IOException {
		this.socket = s1;
		this.inputStream = new DataInputStream(s1.getInputStream());

		this.outputStream = new DataOutputStream(s1.getOutputStream());
	}
	
	@Override
	public void run() { //remember !!!!!!  한사람 서버로 접속한 경우임 
		String nickname = "";
		try {
			if (inputStream != null) {
				nickname = inputStream.readUTF();//초록왕자 , 은빛공주 
				sendChat(nickname + " 님 입장~~~~~ (^^) (^^) (^^) ");
			}
			while (inputStream != null) {
				// System.out.println(inputStream.readUTF());
				sendChat(inputStream.readUTF());  //방가방가 ~~~~
				  // 클라이언트가 보낸 채팅 내용을 접속한  모두에게 보냄
			}//정상채팅의 경우는 계속  while 문안에서 반복 loop 
			
		} catch (IOException e) { //여기로 왔단 얘기는 에러가 발생한 것 //나가버린 경우 
			//e.printStackTrace(); 

		} finally {
			// 나간 쓰레드의 인덱스 찾기
			for (int i = 0; i < threadList.size(); i++) {
				if (socket1.equals(threadList.get(i).socket1)) {//이 소켓이 누구거???????
					threadList.remove(i);//찾았다 홍길동 소켓 - 삭제하자는 
					try {
						sendChat(nickname + " 님 퇴장~~~~~  (ㅠ.ㅠ) (ㅠ.ㅠ) (ㅠ.ㅠ) ");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			System.out.println("접속자 수 : " + threadList.size()+" 명");
		}//finally-end 

	}//run-end 
	
	

}
