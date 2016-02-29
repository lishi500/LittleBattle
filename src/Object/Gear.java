package Object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.jar.Attributes.Name;

import javax.naming.InitialContext;

public abstract class Gear {
	public int id;
	public final int SLOT;
	public String name;
	public String img;
	public String description;
	ArrayList<Skill> skills;
	ArrayList<Attri> attribute_list;
	public Gear(int slot) {
		this.SLOT = slot;
	}
	
	protected abstract void initialGear();
	protected abstract void addAttributeList();
	protected abstract void addSkillList();
	
	public void equip(Role role) {
		RoleAttributes attribute = role.getAttribute();
		for(Attri  attr : attribute_list){
			attribute.addAttriValue(attr);
		} 
	}

	public void un_equip(Role role) {
		RoleAttributes attribute = role.getAttribute();
		for(Attri  attr : attribute_list){
			attribute.dropAttriValue(attr);
		} 
	}

	public ArrayList<Skill> getSkills() {
		return skills;
	}

	public void setSkills(ArrayList<Skill> skills) {
		this.skills = skills;
	}

	public ArrayList<Attri> getAttribute_list() {
		return attribute_list;
	}

	public void setAttribute_list(ArrayList<Attri> attribute_list) {
		this.attribute_list = attribute_list;
	}

	@Override
	public String toString() {
		return "Gear [id=" + id + ", SLOT=" + SLOT + ", name=" + name
				+ "]";
	}

}
