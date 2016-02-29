package Object.Imp;

import Common.Define;
import Object.RoleClass;

public class ArcherClass extends RoleClass{
	public ArcherClass(){
		super();
		name = "Archer";
		class_id = Define.Archer;
	}
	@Override
	public String toString() {
		return name+":["+class_id+"]";
	}
}
