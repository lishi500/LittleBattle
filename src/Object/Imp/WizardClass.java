package Object.Imp;

import Common.Define;
import Object.RoleClass;

public class WizardClass extends RoleClass {
	public WizardClass(){
		super();
		name = "Wizard";
		class_id = Define.Wizard;
	}
	@Override
	public String toString() {
		return name+":["+class_id+"]";
	}
}
