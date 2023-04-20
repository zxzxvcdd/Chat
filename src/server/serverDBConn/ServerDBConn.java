package server.serverDBConn;

import java.sql.Connection;
import java.sql.DriverManager;

public class ServerDBConn {

	private Connection con;

	public ServerDBConn() throws Exception {
			// TODO Auto-generated constructor stub

			Class.forName("oracle.jdbc.driver.OracleDriver");

			con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:" + "1521:xe", "chat", "chat");

		}

	public Connection getCon() {
		return con;
	}

}
