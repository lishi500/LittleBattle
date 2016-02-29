package Data.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Data.Database;
import Object.Role;

public class DAOcommon {
	public ArrayList<String> getlist(int id){
		
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from * where id=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, id);
			ResultSet result =psts.executeQuery();
			
			ArrayList<String> list = new ArrayList<String>();
			while(result.next()){
				String str = result.getString(1);
				list.add(str);
			}
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}
	
	public Object get(int id){
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from * where id=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, id);
			ResultSet result =psts.executeQuery();
			
			while(result.next()){
				int idd = result.getInt("id");
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}
	
	public boolean insert(Object o){

		Connection conn = Database.getInstance().getConnection();
		String SQL = "Insert into * (a) values(?)";
		
		PreparedStatement psts=null;
		try {
			psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			psts.setInt(1, 10);
			int rows = psts.executeUpdate();
			
			int id =0;
			ResultSet rs = psts.getGeneratedKeys();  
			if (rs != null&&rs.next()) {  
			    id=rs.getInt(1); 
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return false;
	}
	
	public boolean update(Object o){
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Update * set xx = ? where id=?";
		
		PreparedStatement psts=null;
		try {
			psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			psts.setInt(1, 10);
			psts.setInt(2, 10);
			int rows = psts.executeUpdate();
			
			if(rows>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return false;
	}
	public boolean check(String condition, int id){
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from * where "+condition+"=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, id);
			ResultSet result =psts.executeQuery();
			
			while(result.next()){
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return false;
	}
}
