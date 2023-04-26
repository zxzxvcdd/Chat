package client.client;

import java.awt.Toolkit;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class RcvThread extends Thread {

	ObjectInputStream ois;
	HashMap<Object, Object> resMap;

	Toolkit tk1 = Toolkit.getDefaultToolkit(); // 비프음
	
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
						EmployeeJoin.joinCall = true;

						break;

					case "afterLogin":
						Login.resMap = resMap;
						Login.call = true;

						break;

					case "afterMain":

						MainViewFrame.call = true;
						MainViewFrame.resMap = resMap;

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


					case "afterSelectByName":

						MainViewFrame.resMap = resMap;
						
						MainViewFrame.call = true;

						break;

					case "afterFindFileList": // ������ ���� �� fileList�� down gui���� ����
						FileDownGUIPlus.call = true;
						FileDownGUIPlus.resMap = resMap;
						if (resMap == null)
							System.out.println("yes");
						else
							System.out.println("yes");
						break;

					case "downContent": // �����κ��� �� file������ downgui���� ����
						FileDownGUIPlus.call = true;
						FileDownGUIPlus.resMap = resMap;
						break;

					case "saveFileFail":
						FileUpGUI.call = true;
						FileUpGUI.resMap = resMap;
						break;

					case "afterReadChat":
					
						for (ChatGUI chat : ChatClient.chatList) {

							
							int rcvId = (Integer) resMap.get("chatId");

							if (chat.getRoom().getChatListDTO().getChatId() == rcvId) {

								chat.setResMap(resMap);
								chat.setCall(true);
								break;

							}

						}

						break;

					case "afterSendChat":

						
						boolean sendResult = (boolean) resMap.get("result");
						if (!sendResult) {
							for (ChatGUI chat : ChatClient.chatList) {
								int rcvId = (Integer) resMap.get("chatId");
								if (chat.getRoom().getChatListDTO().getChatId() == rcvId) {

									chat.setResMap(resMap);
									chat.setCall(true);
									break;

								}

							}
						}
						

						break;
						
					case "send":
						int senIndex =0;
						
		
						if(ChatClient.chatList.size()!=0) {
							System.out.println(1);
						for (ChatGUI chat : ChatClient.chatList) {
							System.out.println(2);
								
							int rcvId = (Integer) resMap.get("chatId");
							if (chat.getRoom().getChatListDTO().getChatId() == rcvId) {

								chat.setResMap(resMap);
								chat.setCall(true);
								break;

							}
							
							if(ChatClient.chatList.size()== senIndex) {
								
								
								//채팅방이 켜져있지 않을 시
								
								
								break;
							}

							senIndex++;
						}
						
//						tk1.beep();
						
						}else {
							
							
							break;
						}

						break;
						
					case "updateRoom":
						
						int upIndex =0;
						for (ChatGUI chat : ChatClient.chatList) {
							
							int rcvId = (Integer) resMap.get("chatId");
							if (chat.getRoom().getChatListDTO().getChatId() == rcvId) {

								chat.setResMap(resMap);
								chat.setCall(true);
								break;

							}
							
							MainViewFrame.call = true;
							MainViewFrame.resMap = resMap;


							upIndex++;
						}
						
						
						break;
						
					case "afterInvite":
						
						int inviteResult = (Integer) resMap.get("inviteResult");
						if (inviteResult!=1) {
							for (ChatGUI chat : ChatClient.chatList) {
								int rcvId = (Integer) resMap.get("chatId");
								if (chat.getRoom().getChatListDTO().getChatId() == rcvId) {

									chat.setResMap(resMap);
									chat.setCall(true);
									break;

								}
								

							}
						}
						
						
						break;
						
					case "afterCreateRoom":
						
						
						String failUsers = (String)resMap.get("CreateResult");
						
						MainViewFrame.call = true;
						MainViewFrame.resMap = resMap;
		
	
						
						break;
						

					default:
						

						break;

					}

				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("rcvThread Error");

		}

	}

}
