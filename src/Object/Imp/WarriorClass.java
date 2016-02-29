package Object.Imp;

import Common.Define;
import Object.RoleClass;

public class WarriorClass extends RoleClass{

	public WarriorClass(){
		this.name = "Warrior";
		this.class_id = Define.Warrior;
		AttactRange = 1;
	}
	public WarriorClass(String name, int id,int class_id) {
		super(name, id,class_id);
		this.name = "Warrior";
		this.class_id = Define.Warrior;
	}
	@Override
	public String toString() {
		return name+":["+class_id+"]";
	}
}
