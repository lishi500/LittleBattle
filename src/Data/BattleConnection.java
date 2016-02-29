package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BattleConnection {
	private String host = "localhost";
	private String port = "3306";
	private String db = "littleBattle";
	private String username = "root";
	private String password = "";
	
	public Connection getConnection() throws  SQLException{
	    try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection("jdbc:mysql://"+host+"/" + db, username, password);
		return conn; 
	}
}
