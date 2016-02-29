package Battle;

import java.util.ArrayList;

import Common.Define;
import Object.Role;

public abstract class Action {
	public int actionMask = 0x111;
	public ArrayList<Role> targets;
	public int identifier;
	public void doGroupAction(Role source, ArrayList<Role> targets, int identifier){
		this.targets = targets;
		for(Role role:targets){
			doAction(source, role, identifier);
		}
	}
	public void doAction(Role source, Role target,int identifier){
		this.identifier = identifier;
		preAction(source,target, this.identifier);
		inAction(source, target,this.identifier);
		afterAction(source, target,this.identifier);
	}
	abstract void preAction(Role source, Role target,int identifier);
	abstract void inAction(Role source, Role target,int identifier);
	abstract void afterAction(Role source, Role target,int identifier);
	
	protected boolean act(int identifier, int mask){
		if((identifier&mask) > 0)
			return true;
		return false;
	}
}
