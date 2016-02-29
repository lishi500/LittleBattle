package Data.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Common.Lib;
import Data.Database;
import Object.Attri;
import Object.Gear;
import Object.Role;
import Object.Skill;
import Object.Imp.SimpleGear;

public class GearDAO {
	
	public Gear getGearById(int gear_id) {
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from Gear where id=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, gear_id);
			ResultSet resutl =psts.executeQuery();
			Gear gear = null;
			while(resutl.next()){
				String name = resutl.getString("name");
				String img = resutl.getString("img");
				int Slot = resutl.getInt("Slot");
				gear = new SimpleGear(Slot);
				gear.name = name;
				gear.id = gear_id;
				gear.img = img;
				setAttribute_list(gear);
				setSkills(gear);
			}
			if(gear!=null)
				return gear;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}
	private void setAttribute_list(Gear gear) {
		
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from gear_attri where gear=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, gear.id);
			ResultSet resutl =psts.executeQuery();
			
			ArrayList<Attri> list = new ArrayList<Attri>();
			while(resutl.next()){
				int attri_id = resutl.getInt("attri");
				Attri attri = new AttriDAO().getAttriById(attri_id);
				if(attri!=null)
					list.add(attri);
			}
			gear.setAttribute_list(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
	}
	
	private void setSkills(Gear gear) {
		ArrayList<Skill> skills = new ArrayList<Skill>();
		gear.setSkills(skills);
	}
	public ArrayList<Gear> getGearsByRole(Role role) {
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from Gear where id in (Select gear from Equipment where role_id=?)";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, role.id);
			ResultSet result =psts.executeQuery();
			
			ArrayList<Gear> list = new ArrayList<Gear>();
			while(result.next()){
				int id = result.getInt("id");
				int slot = result.getInt("slot");
				String name =result.getString("name");
				String img =result.getString("img");
				String description = result.getString("description");
				Gear gear = new SimpleGear(slot);
				gear.id = id;
				gear.name = name;
				gear.img = img;
				gear.description = description;
				
				setGearAttributes(gear);
				setGearSkills(gear);
				list.add(gear);
			}
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}
	private void setGearSkills(Gear gear) {
		// TODO Auto-generated method stub
		
	}
	private void setGearAttributes(Gear gear) {

		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from Attri  where id in (select attri from gear_attri where gear = ? )";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, gear.id);
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
			gear.setAttribute_list(list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
	}
	public ArrayList<Gear> getGearsByPart(int sLOT) {
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from Gear where slot = ?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, sLOT);
			ResultSet result =psts.executeQuery();
			
			ArrayList<Gear> list = new ArrayList<Gear>();
			while(result.next()){
				int id = result.getInt("id");
				int slot = result.getInt("slot");
				String name =result.getString("name");
				String img =result.getString("img");
				Gear gear = new SimpleGear(slot);
				gear.id = id;
				gear.name = name;
				gear.img = img;
				list.add(gear);
			}
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}
	public Gear createGear(Gear newGear) {
		if (newGear==null) {
			return null;
		}
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Insert into Gear(slot,name,img,description) values(?,?,?,?)";
		
		PreparedStatement psts=null;
		try {
			psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			psts.setInt(1, newGear.SLOT);
			psts.setString(2, newGear.name);
			psts.setString(3, newGear.img);
			psts.setString(4, newGear.description);
			int rows = psts.executeUpdate();
			
			int id =0;
			ResultSet rs = psts.getGeneratedKeys();  
			if (rs != null&&rs.next()) {  
			    id=rs.getInt(1); 
			    newGear.id = id;
			    return newGear;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		
		return null;
	}

	

}
