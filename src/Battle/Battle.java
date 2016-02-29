package Battle;

import java.util.ArrayList;

import Object.Map;
import Object.Player;
import Object.Role;

public abstract class Battle implements Runnable, BattleInterface{
	protected ArrayList<Role> group1, group2;
	protected Map battle_ground;
	public float MasterTime;
	public boolean battleEnd;
	public final float FPS = 250.0f;
	public Battle(ArrayList<Role> group1,ArrayList<Role>  group2){
		this.group1 = group1;
		this.group2 = group2;
		MasterTime = 0;
		battleEnd = false;
		startBattle();
	}
	public void startBattle(){
		loadMap();
		assignBattleState();
	}
	private void assignBattleState() {
		int battle_id = 1;
		for(int i=0;i<group1.size();i++){
			Role r = group1.get(i);
			r.state = new BattleState(i, 1, 1,battle_id++);
		}
		for(int i=0;i<group2.size();i++){
			Role r = group2.get(i);
			r.state = new BattleState(i, 1, 2,battle_id++);

		}
	}
	private void loadMap() {
		
	}
	protected abstract boolean checkBattleState();
	public abstract void combat(int nextAction, Role source, ArrayList<Role> targets, int select_id);
	
	public ArrayList<Role> getAllies(Role role){
		if(role.state.group==1)
			return group1;
		else 
			return group2;
	}
	public ArrayList<Role> getEnemies(Role role){
		if(role.state.group==1)
			return group2;
		else 
			return group1;
	}
	public Role getRoleByBattleStateID(int state_id){
		for(Role r:group1){
			if(r.state.ID==state_id)
				return r;
		}
		for(Role r:group2){
			if(r.state.ID==state_id)
				return r;
		}
		return null;
	}
	
}
