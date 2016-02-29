package Data.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Common.Define;
import Data.Database;
import Object.Role;
import Object.Skill;
import Object.SkillAttribute;
import Object.Imp.SingleTargetSkill;

public class SkillDAO {

	public ArrayList<Skill> getSkillSetByRole(Role r) {
	
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from role_skill where role=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, r.id);
			ResultSet result =psts.executeQuery();
			
			ArrayList<Skill> skills = new ArrayList<Skill>();
			while(result.next()){
				int role_id =result.getInt("role") ;
				int  skill_id  =result.getInt("skill") ;
				Skill skill = getSkillByID(skill_id);
				skills.add(skill);
			}
			return skills;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}
	public Skill getSkillByID(int skill_id){
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from Skill where id=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, skill_id);
			ResultSet result =psts.executeQuery();
			ArrayList<Skill> skills = processSkillList(result);
			if(skills!=null && skills.size()>0)
				return skills.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		
		return null;
	}
	
	public ArrayList<Skill> getAllSkills() {

		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from Skill";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			ResultSet result =psts.executeQuery();
			return processSkillList(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		return null;
	}

	public  ArrayList<Skill> processSkillList(ResultSet result) throws SQLException{
		ArrayList<Skill> list = new ArrayList<Skill>();
		while(result.next()){
			Skill skill = null;
			 int id = result.getInt(1);
			 int level = result.getInt("level");
			 String name = result.getString("name");
			 int selectType = result.getInt("selectType");//select
			 int targetType = result.getInt("targetType");
			 float cooldown = result.getInt("cooldown");
			 float manacost = result.getFloat("consume");
			 int area = result.getInt("area");
			 String description = result.getString("description");
			 String img = result.getString("img");
			 float triggerChance = result.getFloat("triggerChance");
			 int triggerType = result.getInt("triggerType");
			 switch (targetType) {
			case Define.SKILL_SINGLE:
				skill = new SingleTargetSkill(id, level, name, selectType, targetType, cooldown, manacost, description, area, triggerChance,triggerType);
				System.out.println("Single Target");
				break;
			case Define.SKILL_GROUP:
				System.out.println("Group Target");			
				break;
			case Define.SKILL_AURA:
				System.out.println("AURA Target");
				break;
			default:
				break;
			}
			 setSkillAttribute(skill);
			 setSkillEffect(skill);
			 list.add(skill);
		}
		
		return list;
	}
	
	private void setSkillEffect(Skill skill) {
		// TODO Auto-generated method stub
		
	}

	private void setSkillAttribute(Skill skill) {
		Connection conn = Database.getInstance().getConnection();
		String SQL = "Select * from skill_attribute where skill=?";
		try {
			PreparedStatement psts = conn.prepareStatement(SQL);
			psts.setInt(1, skill.id);
			ResultSet result =psts.executeQuery();
			
			ArrayList<SkillAttribute> list = new ArrayList<SkillAttribute>();
			while(result.next()){
				int id  =result.getInt(1) ;
				float base =result.getFloat("base") ;
				float  scale  =result.getFloat("scale") ;
				int valueType  =result.getInt("valueType") ;
				int damageType  =result.getInt("damageType") ;
				int scaleType  =result.getInt("scaleType") ;
				SkillAttribute skillAttribute = new SkillAttribute(id, base, scale, valueType, damageType, scaleType);
			    list.add(skillAttribute);
			}
			skill.setAttributeList(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Database.getInstance().releaseConnection(conn);
		}
		
	}

}
