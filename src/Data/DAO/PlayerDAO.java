package Data.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Data.Database;
import Object.Player;
import Object.Imp.PlayerImp;

public class PlayerDAO{
	
	public Player[] getAllPlayer(){
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from Player";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			ResultSet resutl =psts.executeQuery();
			ArrayList<Player> players = new ArrayList<Player>();
			while(resutl.next()){
				int id = resutl.getInt(1);
				String username = resutl.getString("username");
				String nickname = resutl.getString("nickname");
				String password = resutl.getString("password");
				String email = resutl.getString("email");
				Player p = new PlayerImp(id, username, nickname);
				p.password = password;
				p.email = email;
				players.add(p);
			}
			return players.toArray(new PlayerImp[players.size()]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Player createPlayer(Player player){
		boolean success = false;
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Insert into Player(username,password,nickname,email) values(?,?,?,?)";
		
		PreparedStatement psts=null;
		try { 
			psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			psts.setString(1, player.username);
			psts.setString(2, player.password);
			psts.setString(3, player.nickname);
			psts.setString(4, player.email);
			int id2 = psts.executeUpdate();
			int id =0;
			ResultSet rs = psts.getGeneratedKeys();  
			if (rs != null&&rs.next()) {  
			    id=rs.getInt(1); 
			    player.id = id;
			}  
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return player;
	}
	public boolean updatePlayer(Player player){
		return true;
	}
	public PlayerDAO() {
		// TODO Auto-generated constructor stub
	}
}
