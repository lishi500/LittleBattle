package Data.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Data.Database;
import Map.MapSegment;

public class MapSegmentDAO {
	
	public ArrayList<MapSegment> getall(){
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from map_segment";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			ResultSet result =psts.executeQuery();
			
			ArrayList<MapSegment> list = new ArrayList<MapSegment>();
			while(result.next()){
				int id = result.getInt(1);
				String name  = result.getString(2);
				String type = result.getString(3);
				String file_path = result.getString(4); //file of origonal bitmap 
				int xpos = result.getInt(5); 
				int ypos = result.getInt(6);
				int width = result.getInt(7);
				int height = result.getInt(8);
			
				MapSegment mapSegment = new MapSegment(id, name, type, file_path, xpos, ypos, width, height);
				list.add(mapSegment);
			}
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}
	
	public ArrayList<MapSegment> getlistByType(String type_str){
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from map_segment where type=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setString(1, type_str);
			ResultSet result =psts.executeQuery();
			
			ArrayList<MapSegment> list = new ArrayList<MapSegment>();
			while(result.next()){
				int id = result.getInt(1);
				String name  = result.getString(2);
				String type = result.getString(3);
				String file_path = result.getString(4); //file of origonal bitmap 
				int xpos = result.getInt(5); 
				int ypos = result.getInt(6);
				int width = result.getInt(7);
				int height = result.getInt(8);
			
				MapSegment mapSegment = new MapSegment(id, name, type, file_path, xpos, ypos, width, height);
				list.add(mapSegment);
			}
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}
	
	public MapSegment get(int id){
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from map_segment where id=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, id);
			ResultSet result =psts.executeQuery();
			
			while(result.next()){
				int ID = result.getInt(1);
				String name  = result.getString(2);
				String type = result.getString(3);
				String file_path = result.getString(4); //file of origonal bitmap 
				int xpos = result.getInt(5); 
				int ypos = result.getInt(6);
				int width = result.getInt(7);
				int height = result.getInt(8);
			
				MapSegment mapSegment = new MapSegment(ID, name, type, file_path, xpos, ypos, width, height);
				return mapSegment;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}
	
	public boolean insert(MapSegment mapSegment){

		Connection conn = Database.getInstance().getConnection();
		String SQL = "Insert into map_segment (name,type,file_path,xpos,ypos,width,heigh) values(?,?,?,?,?,?,?)";
		
		PreparedStatement psts=null;
		try {
			psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			psts.setString(1, mapSegment.name);
			psts.setString(2, mapSegment.type);
			psts.setString(3, mapSegment.file_path);
			psts.setInt(4, mapSegment.xpos);
			psts.setInt(5, mapSegment.ypos);
			psts.setInt(6, mapSegment.width);
			psts.setInt(7, mapSegment.height);
			int rows = psts.executeUpdate();
			
			int id =0;
			ResultSet rs = psts.getGeneratedKeys();  
			if (rs != null&&rs.next()) {  
			    id=rs.getInt(1); 
			    return true;
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return false;
	}
	
	public boolean delete(MapSegment mapSegment){
		Connection conn = Database.getInstance().getConnection();
		String SQL = "delete from map_segment where id=?";
		
		PreparedStatement psts=null;
		try {
			psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			psts.setInt(1, mapSegment.id);
			
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
	
}
