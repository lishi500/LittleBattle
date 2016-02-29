package Data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;

public class Database {
	private static Database database;
	private Database(){
		CurrentPoolSize = 0;
		conn_pool = new LinkedBlockingQueue<Connection>();
	}
	public static Database getInstance(){
		if(database==null){
			synchronized (Database.class) {
				if(database==null)
					database = new Database();
			}
		}
		return database;
	}
	
	
	private LinkedBlockingQueue<Connection> conn_pool;
	private final int MaxCapacity = 12;
	private int CurrentPoolSize;
	
	public Connection getConnection(){
		try {
			if(!conn_pool.isEmpty()){
				return validateConnection(conn_pool.take());
			}else{
				if(CurrentPoolSize >= MaxCapacity){
					return null;
				}else{
					addConnection();
					return getConnection();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void releaseConnection(Connection conn){
		conn_pool.add(conn);
	}
	
	
	private Connection validateConnection(Connection conn) throws SQLException{
		if(conn.isClosed()){
			conn = getConnect();
		}
		return conn;
	}
	private void addConnection() throws SQLException, InterruptedException {
		Connection connection = getConnect();
		if(CurrentPoolSize<MaxCapacity){
			conn_pool.put(connection);
			CurrentPoolSize++;
		}
	}
	private Connection getConnect() throws SQLException{
		return new BattleConnection().getConnection();
	}
}
