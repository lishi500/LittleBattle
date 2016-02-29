package Data.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Data.Database;
import Object.Equipment;
import Object.Gear;
import Object.Role;
import Object.Imp.EquipmentImp;

public class EquipmentDAO {

	

	private void equip(Equipment equipment, int gear_id) {
		Gear gear = new GearDAO().getGearById(gear_id);
		equipment.equip(equipment.getRole(), gear);
	}

	public Equipment getEquipmentByRole(Role r) {
		ArrayList<Gear> gears = new GearDAO().getGearsByRole(r);
		if(gears!=null){
			Equipment equipment = new EquipmentImp(r);
			equipment.setRole(r);
			for(Gear gear: gears){
				if(gear!=null){
					equipment.equip(r, gear);
				}
			}
			return equipment;
			
		}
		return null;
	}

	public void equipGearToRole(Gear gear, Role role) {
		String SQL ="";
		Connection conn = Database.getInstance().getConnection();
		PreparedStatement psts=null;
		if(checkIfEquiped(gear.SLOT,role.id)){
			SQL = "Update Equipment set gear = ? where role_id=? and slot = ?";
			try {
				psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
				psts.setInt(1, gear.id);
				psts.setInt(2, role.id);
				psts.setInt(3, gear.SLOT);
				int rows = psts.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				Database.getInstance().releaseConnection(conn);
			}
		}else{
			SQL = "Insert into Equipment(role_id,gear,slot) values(?,?,?)";	
			try {
				psts = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
				psts.setInt(1, role.id);
				psts.setInt(2, gear.id);
				psts.setInt(3, gear.SLOT);
				
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
		}
	}
	
	public boolean checkIfEquiped(int slot, int roleId){
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from Equipment where slot=? and role_id = ?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, slot);
			psts.setInt(2, roleId);
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

