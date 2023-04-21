package client.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class SendChat extends Thread {

	ObjectOutputStream oos;
	Socket s1;

	static HashMap<Object, Object> reqMap;

	public SendChat(Socket s1) {
		// TODO Auto-generated constructor stub

		try {

			this.s1 = s1;
			oos = new ObjectOutputStream(s1.getOutputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		try {

			while (true) {
				if (reqMap.get("command") != null) {

					String command = "";
					String window = null;

					oos.writeObject(reqMap);
					oos.flush();

				}

				resMap.remove("command");

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
