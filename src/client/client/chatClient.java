package client.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatClient {

	public ChatClient(String ip, int port) {
		
		
		private Socket socket;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
	    
		
		try {
			// ������ ��û ������
			socket = new Socket(ip, port);
			System.out.println(socket.getInetAddress().getHostAddress() + " �����");

			oos = new ObjectOutputStream(socket.getOutputStream());

			ArrayList<BoardVO> list = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				BoardVO vo = new BoardVO();
				vo.setTitle(i + "��° �����Դϴ�!");
				vo.setContent("1234567890_testtest String ���ڿ� �׽�Ʈ ����������");
				vo.setIdx(i);
				vo.setWriter("ȫ�浿");
				list.add(vo);
			}

			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("list", list);
			// VO �޽��� �߼�
			oos.writeObject(map);
			oos.flush();

			// �߼� �� �޽��� �ޱ�
			ois = new ObjectInputStream(socket.getInputStream());
			// ���� ���
			HashMap<Object, Object> returnMap = (HashMap<Object, Object>) ois.readObject();
			ArrayList<BoardVO> retrunList = (ArrayList<BoardVO>) returnMap.get("list");
			for (int i = 0; i < retrunList.size(); i++) {
				System.out.println(retrunList.get(i).toString());
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			// ���� �ݱ� (���� ����)
			try {
				if (socket != null) {
					socket.close();
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
