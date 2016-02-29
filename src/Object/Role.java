package Object;

import java.util.ArrayList;

import Battle.BattleState;
import Common.Define;

public abstract class Role {
	public Role(int id) {
		super();
		this.id = id;
	} 
	public Role(){}
	public String name;
	public int id;
	public Player player;
	protected ArrayList<Skill> skills;
	protected Equipment equipment;
	protected RoleAttributes attribute;
	protected RoleClass rollClass;
	public BattleState state;
	
	public ArrayList<Skill> getSkills() {
		return skills;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public RoleAttributes getAttribute() {
		return attribute;
	}
	public RoleClass getRollClass() {
		return rollClass;
	}
	public int getId(){return id;}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public void setSkills(ArrayList<Skill> skills) {
		this.skills = skills;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public void setAttribute(RoleAttributes attribute) {
		this.attribute = attribute;
	}
	public void setRollClass(RoleClass rollClass) {
		this.rollClass = rollClass;
	}
	public void skillCoolDown(){
		if(skills!=null){
			for(Skill skill : skills){
				skill.cooldown();
			}
		}
	}
	@Override
	public String toString() {
		return name+"["+ Define.CLASS_TYPE[(rollClass!=null)?(rollClass.class_id):0] +"]";
	}
	public void toPrint(){
		System.out.println(toString());
		if(attribute!=null)
			System.out.println(attribute.toString());
		if(equipment!=null)
			equipment.toPrint();
	}
}
