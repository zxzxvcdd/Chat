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
					
					System.out.println("resMap : " + resMap);
					
					if (resMap != null) {
					String command = (String) resMap.get("command");

					System.out.println("coammand : " + command);

					switch (command) {

					case "afterJoin":
						EmployeeJoin.resMap = resMap;
						EmployeeJoin.call = true;
						
						System.out.println("Call 변경" + EmployeeJoin.call);
						

						break;

					case "afterLogin":
						Login.resMap = resMap;
						System.out.println("afterLogin");
						Login.call = true;

						

						break;
//
//				case "afterUpdate":
//						
//					Employee_Update.call = true;
//					Employee_Update.resMap = resMap;
//					
//					break;
//					
//				case "afterAccreditation":
//					
//					Accreditation.call = true;
//					Accreditation.resMap = resMap;
//					break;
//					

					case "chat":
						Toolkit tk1 = Toolkit.getDefaultToolkit(); // 비프음

						int chatId = (Integer) resMap.get("ChatId");

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

					case "afterSelectByName":
						
						EmployeeSearch.resMap = resMap;
						System.out.println("search : "+EmployeeSearch.resMap);
						EmployeeSearch.call = true;
						System.out.println(EmployeeSearch.call);

						

						break;

					default:

						break;

					}

				}
			}

		} catch (Exception e) {
			
			System.out.println("rcvThread Error");

		}

	}

}
