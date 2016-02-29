package Data.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Data.Database;
import Object.Attri;
import Object.LevelGrowth;
import Object.Role;
import Object.RoleAttributes;
import Object.RoleClass;
import Object.Imp.RoleAttributeImp;
import Object.Imp.SimpleLevelUp;
import Object.Imp.SimpleRoleClass;

public class RoleClassDAO {

	public RoleClass getRoleClassByRole(Role r) {
		if(r.getRollClass()==null)
			return null;
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from roleClass where class_id=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, r.getRollClass().class_id);
			ResultSet resutl =psts.executeQuery();
			
			RoleClass roleClass = null;
			while(resutl.next()){
				String name = resutl.getString("name");
				int id = resutl.getInt("id");
				int class_id = resutl.getInt("class_id");
				roleClass = (RoleClass) Class.forName("Object.Imp."+name+"Class").newInstance();
				setGrowth(roleClass);
				setInitial(roleClass);
				roleClass.setName(name);
				roleClass.id = id ;
				roleClass.class_id = class_id;
			}
			return roleClass;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}

	private void setInitial(RoleClass roleClass) {
//rollClass_initial
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from roleClass_initial where roleClass=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, roleClass.id);
			ResultSet resutl =psts.executeQuery();
			
			ArrayList<Attri> list = new ArrayList<Attri>();
			while(resutl.next()){
				int attri_id = resutl.getInt("attri");
				Attri attri = new AttriDAO().getAttriById(attri_id);
				if(attri!=null)
					list.add(attri);
			}
			RoleAttributes attributes = new RoleAttributeImp();
			for(Attri attri: list){
				attributes.setInitialAttri(attri);
			}
			roleClass.setInitial(attributes);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
	}

	private void setGrowth(RoleClass roleClass) {
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from roleClass_groth where roleClass=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, roleClass.id);
			ResultSet resutl =psts.executeQuery();
			
			ArrayList<Attri> list = new ArrayList<Attri>();
			while(resutl.next()){
				int attri_id = resutl.getInt("attri");
				Attri attri = new AttriDAO().getAttriById(attri_id);
				if(attri!=null)
					list.add(attri);
			}
			LevelGrowth growth = new SimpleLevelUp();
			for(Attri attri: list){
				growth.setInitialAttri(attri);
			}
			roleClass.setGrowth(growth);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
	}

	public ArrayList<RoleClass> getAllRoleClass() {
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from roleClass";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			ResultSet resutl = psts.executeQuery();
			
			ArrayList<RoleClass> list = new ArrayList<RoleClass>();
			while(resutl.next()){
				RoleClass roleClass = null;
				String name = resutl.getString("name");
				int id = resutl.getInt("id");
				int class_id = resutl.getInt("class_id");
				
				roleClass = new SimpleRoleClass(name, id, class_id);
//				setGrowth(roleClass);
//				setInitial(roleClass);
//				roleClass.setName(name);
//				roleClass.id = id ;
				list.add(roleClass);
			}
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}

}
