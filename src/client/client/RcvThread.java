package client.client;

import java.awt.Toolkit;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class RcvThread extends Thread {

	ObjectInputStream ois;
	HashMap<Object, Object> resMap;

	public RcvThread(ObjectInputStream ois) {
		// TODO Auto-generated constructor stub

		this.ois = ois;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {

			while (true) {

				resMap = (HashMap<Object, Object>) ois.readObject();

				
				String command = (String) resMap.get("command");

				switch (command) {

				case "afterJoin":
				case "afterLogin":
					System.out.println(command);
					ClientTestFrame.call = true;
					System.out.println(ClientTestFrame.call);
					ClientTestFrame.resMap = resMap;
					
					break;
					
					
				case "chat":
					Toolkit tk1=Toolkit.getDefaultToolkit(); //������
					
					
					int chatId = (Integer)resMap.get("ChatId");
					
//					for(ChatRoomFrame Room : ChatClient.chatList) {
//						
//						if(room.getChatId == chatId) {
//							
//							room.call = true;
//							room.resMap = resMap;
//							break;
//							
//						}
//						
//					}
					tk1.beep();
					break;
					
					
					

				
					

				default:

					break;

				}

			}

		} catch (Exception e) {

		}

	}

}