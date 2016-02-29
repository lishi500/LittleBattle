package Data.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Data.Database;
import Object.Attri;
import Object.Role;
import Object.RoleAttributes;
import Object.RoleClass;
import Object.Imp.RoleAttributeImp;

public class RoleAttibutesDAO {

	public RoleAttributes getRollAttributesByRole(Role r) {
		if(r==null)
			return null;
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from role_attri where role=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, r.getId());
			ResultSet resutl =psts.executeQuery();
			ArrayList<Attri> attriList = new ArrayList<Attri>();
			while(resutl.next()){
				int attri_id = resutl.getInt("attri");
				Attri attri = new AttriDAO().getAttriById(attri_id);
				if(attri!=null)
					attriList.add(attri);
			}
			RoleAttributes  roleAttributes = new  RoleAttributeImp();
			if(attriList.size()>0){
				for(Attri attri : attriList){
					roleAttributes.setInitialAttri(attri);
				}
			}
			return roleAttributes;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}

}
