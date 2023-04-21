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
			// 서버에 요청 보내기
			socket = new Socket(ip, port);
			System.out.println(socket.getInetAddress().getHostAddress() + " 연결됨");

			oos = new ObjectOutputStream(socket.getOutputStream());

			ArrayList<BoardVO> list = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				BoardVO vo = new BoardVO();
				vo.setTitle(i + "번째 제목입니다!");
				vo.setContent("1234567890_testtest String 문자열 테스트 컨텐츠츠츠");
				vo.setIdx(i);
				vo.setWriter("홍길동");
				list.add(vo);
			}

			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("list", list);
			// VO 메시지 발송
			oos.writeObject(map);
			oos.flush();

			// 발송 후 메시지 받기
			ois = new ObjectInputStream(socket.getInputStream());
			// 응답 출력
			HashMap<Object, Object> returnMap = (HashMap<Object, Object>) ois.readObject();
			ArrayList<BoardVO> retrunList = (ArrayList<BoardVO>) returnMap.get("list");
			for (int i = 0; i < retrunList.size(); i++) {
				System.out.println(retrunList.get(i).toString());
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			// 소켓 닫기 (연결 끊기)
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
