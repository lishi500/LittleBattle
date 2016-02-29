package Data.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JList;

import Common.Define;
import Common.Lib;
import Data.Database;
import Object.Attri;
import Object.Gear;
import Object.Role;
import Object.ValueType;

public class AttriDAO {

	public Attri getAttriById(int attri_id) {
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from Attri where id=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, attri_id);
			ResultSet result =psts.executeQuery();
			
			while(result.next()){
				int id = result.getInt("id");
				int defineId = result.getInt("attri_id");
				String name = result.getString("attri_name");
				int type = result.getInt("type");
				String value = result.getString("value");
				Attri attri = new Attri(id, name, defineId, type, Lib.getInstance().getStringValueToObject(value, type));
				return attri;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}

	public ArrayList<Attri> getAllAttriDefine() {
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from  Attri_define";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			ResultSet result =psts.executeQuery();
			
			ArrayList<Attri> list = new ArrayList<Attri>();
			while(result.next()){
				int id = result.getInt(1);
				String name = result.getString("name");
				int dataType = result.getInt("type");
				Object instace = Define.TYPE_OBJECT_Instance[dataType];
				
				Attri attri = new Attri(name, id, dataType, instace);
				list.add(attri);
			}
			return list;
			
		} catch (SQLException  e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}

	public Attri createNewAttribute(Attri attri) {
		if(attri.getDefineID()==-1){
			attri = createNewAttri_define(attri);
			if(attri==null)
				return null;
		}
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Insert into Attri (attri_id,attri_name,type,value) values(?,?,?,?)";
		
		PreparedStatement psts=null;
		try {
			psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			psts.setInt(1, attri.getDefineID());
			psts.setString(2, attri.getName());
			psts.setInt(3, attri.getType());
			psts.setString(4, attri.getValueInString());
			int rows = psts.executeUpdate();
			
			int id =0;
			ResultSet rs = psts.getGeneratedKeys();  
			if (rs != null&&rs.next()) {  
			    id=rs.getInt(1); 
			    attri.setId(id);
			    return attri;
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}
	
	private Attri createNewAttri_define(Attri attri) {
		
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Insert into Attri_define (name,type) values(?,?)";
		
		PreparedStatement psts=null;
		try {
			psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			psts.setString(1, attri.getName());
			psts.setInt(2, attri.getType());
			int rows = psts.executeUpdate();
			
			int id =0;
			ResultSet rs = psts.getGeneratedKeys();  
			if (rs != null&&rs.next()) {  
			    id=rs.getInt(1); 
			    attri.setDefineID(id);
				return attri;
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}

	public boolean linkAttriTo(String string, Attri attri_clone, Object obj) {
		int id=-1;
		String SQL = "";
		if(string.equals("Role")){
			SQL = "Insert into role_attri(role,attri) values(?,?)";
			id = ((Role)obj).getId();
		}else if(string.equals("Gear")){
			SQL = "Insert into gear_attri(gear,attri) values(?,?)";
			id = ((Gear)obj).id;
		}
		
		Connection conn = Database.getInstance().getConnection();
		PreparedStatement psts=null;
		
		try {
			psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			psts.setInt(1, id);
			psts.setInt(2, attri_clone.id);
			int rows = psts.executeUpdate();
			
			int newid =0;
			ResultSet rs = psts.getGeneratedKeys();  
			if (rs != null&&rs.next()) {  
			    newid=rs.getInt(1); 
			    return true;
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return false;
	}

	public void removeAttriById(int id) {
		Connection conn = Database.getInstance().getConnection();
		PreparedStatement psts=null;
		
		String	SQL = "DELETE FROM Attri WHERE id=?";
		
		try {
			psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			psts.setInt(1, id);
			int rows = psts.executeUpdate();
			
			int newid =0;
			ResultSet rs = psts.getGeneratedKeys();  
			if (rs != null&&rs.next()) {  
			    newid=rs.getInt(1); 
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
	}
	public boolean removeAttriLink(String str ,Attri attri, Object obj){
		int id=-1;
		String SQL = "";
		if(str.equals("Role")){
			SQL = "DELETE FROM role_attri WHERE role=? and attri=?";
			id = ((Role)obj).getId();
		}else if(str.equals("Gear")){
			SQL = "DELETE FROM gear_attri WHERE gear=? and attri=?";
			id = ((Gear)obj).id;
		}
		
		Connection conn = Database.getInstance().getConnection();
		PreparedStatement psts=null;
		
		try {
			psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			psts.setInt(1, id);
			psts.setInt(2, attri.id);
			int rows = psts.executeUpdate();
			
			int newid =0;
			ResultSet rs = psts.getGeneratedKeys();  
			if (rs != null&&rs.next()) {  
			    newid=rs.getInt(1); 
			    return true;
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return false;
	}

	public ArrayList<Attri> getAttriByGear(Gear g) {
		Connection conn = Database.getInstance().getConnection();
		String SQL = "select * from Attri where id in (select attri from gear_attri where gear=?)";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, g.id);
			ResultSet result =psts.executeQuery();
			
			ArrayList<Attri> list = new ArrayList<Attri>();
			while(result.next()){
				int id = result.getInt("id");
				int defineId = result.getInt("attri_id");
				String name = result.getString("attri_name");
				int type = result.getInt("type");
				String value = result.getString("value");
				Attri attri = new Attri(id, name, defineId, type, Lib.getInstance().getStringValueToObject(value, type));
				list.add(attri);
			}
			return list;
			
		} catch (SQLException  e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}

	public void deleteAttriFromGear(Attri attri, Gear selectedGear) {
		removeAttriLink("Gear", attri, selectedGear);
		removeAttriById(attri.id);
	}

}
