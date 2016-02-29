package Data.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Data.Database;
import Editor.SkillPanel;
import Object.Equipment;
import Object.Player;
import Object.Role;
import Object.RoleAttributes;
import Object.RoleClass;
import Object.Skill;
import Object.Imp.PlayerImp;
import Object.Imp.RoleImp;
import Object.Imp.SimpleRoleClass;

public class RoleDAO {
	public ArrayList<Role> getRolesByPlayer(Player player){
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from Role where play_id=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, player.id);
			ResultSet resutl =psts.executeQuery();
			ArrayList<Role> roles = new ArrayList<Role>();
			while(resutl.next()){
				int id = resutl.getInt("id");
				int play_id = resutl.getInt("play_id");
				int class_id = resutl.getInt("class_id");
				String name = resutl.getString("name");
				Role r = new RoleImp(id);
				r.name = name;
				r.setPlayer(player);
				setRollClass(r,class_id);
				setAttribute(r);
				setSkills(r);
				setEquipment(r);
				roles.add(r);
			}
			return roles;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}
	
	public Role getRoleById(int role_id){
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from Role where id=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, role_id);
			ResultSet resutl =psts.executeQuery();

			while(resutl.next()){
				int id = resutl.getInt("id");
				int play_id = resutl.getInt("play_id");
				int class_id = resutl.getInt("class_id");
				String name = resutl.getString("name");
				Role r = new RoleImp(id);
				r.name = name;
//				r.setPlayer(player);
				setRollClass(r,class_id);
				setAttribute(r);
				setSkills(r);
				setEquipment(r);
				return r;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}

	public boolean AddRoleToPlayer(Player p, int RollClassId,String name){
		boolean success = false;
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Insert into Role(play_id,class_id,name) values(?,?,?)";
		
		PreparedStatement psts=null;
		try {
			psts = conn.prepareStatement(SQL);
			psts.setInt(1, p.id);
			psts.setInt(2, RollClassId);
			psts.setString(3, name);
			success = psts.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return success;
	} 
	private void setSkills(Role r) {
		ArrayList<Skill> skills =new SkillDAO().getSkillSetByRole(r);
		r.setSkills(skills);
	}
  
	private void setRollClass(Role r,int class_id) {
		RoleClass rollClass = new SimpleRoleClass();
		rollClass.class_id =class_id;
		r.setRollClass(rollClass);
		rollClass = new RoleClassDAO().getRoleClassByRole(r);
		r.setRollClass(rollClass);
	}

	private void setEquipment(Role r) {
		Equipment equipment = new EquipmentDAO().getEquipmentByRole(r);
		r.setEquipment(equipment);
	}

	private void setAttribute(Role r) {
		RoleAttributes attributes = new RoleAttibutesDAO().getRollAttributesByRole(r);
		if(attributes==null)
			System.out.println("attributes null");
		r.setAttribute(attributes);
	}

	public Role createRole(Player current, Role r) {
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Insert into Role(play_id,class_id,name)  values(?,?,?)";
		
		PreparedStatement psts=null;
		try {
			psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			psts.setInt(1, current.id);
			psts.setInt(2, r.getRollClass().class_id);
			psts.setString(3, r.name);
			
			int rows = psts.executeUpdate();
			
			int id =0;
			ResultSet rs = psts.getGeneratedKeys();  
			if (rs != null&&rs.next()) {  
			    id=rs.getInt(1); 
			  	r.id = id;
			  	return r;
			} 

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return r;
	}
}


