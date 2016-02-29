package Battle;

import java.util.ArrayList;

import Common.Define;
import Object.Effact;
import Object.Role;

public class BattleState {
	public int state;
	public float count;
	public int xpos,ypos;// x row , y column
	public int round;
	public int group;
	public final int ID; 
	public ArrayList<Effact> buff,debuff,aura;
	public int nextAction;

	
	
	public BattleState(int xpos, int ypos,int group, int ID){
		state = Define.WAIT;
		count = 0;
		round = 0;
		this.xpos = xpos;
		this.ypos = ypos;
		this.group = group;
		this.ID = ID;
		buff  = new ArrayList<Effact>();
		debuff = new ArrayList<Effact>();
		aura = new ArrayList<Effact>();
		
	}
	
	public float trigger(int actionCode, float value, Role source, Role target){
		value = triggerAura(actionCode, value, source, target);
		value = triggerBuff(actionCode, value, source, target);
		value = triggerDefuff(actionCode, value, source, target);

		return value;
	}
	


	private float triggerDefuff(int actionCode, float value, Role source,
			Role target) {
		// TODO Auto-generated method stub
		return value;
	}

	private float triggerBuff(int actionCode, float value, Role source, Role target) {
		for(Effact effact : buff){
			int reaction = effact.trigger(actionCode);
			
		}
		return value;
	}

	private float  triggerAura(int actionCode, float value, Role source, Role target) {
		// TODO Auto-generated method stub
		return value;
	}
	
	
}
