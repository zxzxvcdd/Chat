package client.client;

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

				case "join":

					ClientTestFrame.call = true;
					ClientTestFrame.resMap = resMap;
					
					break;
					
					
				case "login":
					
					break;
				
					

				default:

					break;

				}

			}

		} catch (Exception e) {

		}

	}

}
