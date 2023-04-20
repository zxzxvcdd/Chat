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
	public void run() { //remember !!!!!!  �ѻ�� ������ ������ ����� 
		String nickname = "";
		try {
			if (inputStream != null) {
				nickname = inputStream.readUTF();//�ʷϿ��� , �������� 
				sendChat(nickname + " �� ����~~~~~ (^^) (^^) (^^) ");
			}
			while (inputStream != null) {
				// System.out.println(inputStream.readUTF());
				sendChat(inputStream.readUTF());  //�氡�氡 ~~~~
				  // Ŭ���̾�Ʈ�� ���� ä�� ������ ������  ��ο��� ����
			}//����ä���� ���� ���  while ���ȿ��� �ݺ� loop 
			
		} catch (IOException e) { //����� �Դ� ���� ������ �߻��� �� //�������� ��� 
			//e.printStackTrace(); 

		} finally {
			// ���� �������� �ε��� ã��
			for (int i = 0; i < threadList.size(); i++) {
				if (socket1.equals(threadList.get(i).socket1)) {//�� ������ ������???????
					threadList.remove(i);//ã�Ҵ� ȫ�浿 ���� - �������ڴ� 
					try {
						sendChat(nickname + " �� ����~~~~~  (��.��) (��.��) (��.��) ");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			System.out.println("������ �� : " + threadList.size()+" ��");
		}//finally-end 

	}//run-end 
	
	

}
