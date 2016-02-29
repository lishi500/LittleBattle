package Battle;

import java.awt.peer.LightweightPeer;
import java.util.ArrayList;
import java.util.Random;

import Common.Define;
import Common.Lib;
import Object.Player;
import Object.Role;
import Object.Skill;

public class BasicBattle extends Battle {

	private boolean battleTimer = true;
	public BasicBattle(ArrayList<Role> group1, ArrayList<Role> group2) {
		super(group1, group2);
	}

	@Override
	public void run() {
		while(!battleEnd){
			checkBattleState();
			try {
				Thread.sleep((long) FPS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void combat(int nextAction,Role source, ArrayList<Role> targets,int select_id) {
		switch(nextAction){
		case Define.ACTION_ATTACK:
			if(source.getRollClass().AttactRange==1){
				for(Role r : targets){
					if(r.state.ypos == source.state.ypos){
						AttactAction attactAction = new AttactAction();
						attactAction.doGroupAction(source, Lib.getInstance().toRoleGroup(r), Define.DO_ALL_ACTION);
					}
				}
			}
			break;
		case Define.ACTION_DEFENCE:
			break;
		case Define.ACTION_SKILL:
			for(Role r:targets){
				SkillAction skillAction = new SkillAction(Lib.getInstance().getSkillFromRole(source, select_id));
				skillAction.doGroupAction(source, targets, Define.DO_ALL_ACTION);
			}
			break;	
		case Define.ACTION_ITEM:
			break;
		case Define.ACTION_SKIP:
			break;
		case Define.ACTION_RUN_AWAY:
			break;
		 default:
			break;
		}
		
		
	}

	@Override
	protected boolean checkBattleState() {
		checkGroup(group1);
		if(!battleEnd)
			checkGroup(group2);
		return false;
	}
	private void checkGroup(ArrayList<Role> group){
		boolean allDied = true;
		for(Role role : group){
			if(role.getAttribute().isAlive())
				allDied = false;
			if(battleTimer)
				role.state.count += FPS/10;
			if(role.state.count>role.getAttribute().getByFieldName("Speed").getFloat() && role.getAttribute().isAlive()){
				poseActionTimer();
				role.skillCoolDown();
				role.state.count -=role.getAttribute().getByFieldName("Speed").getFloat();
				//--------------------------------------------------------------------
				BattleControl bc = new BattleControl(role, getAllies(role), getEnemies(role));
				int nextMove = bc.AskForAction();
				int Action = Lib.getInstance().actionDecoder_Action(nextMove);
				ArrayList<Role> targetList = new ArrayList<Role>();
				Role target1 = getRoleByBattleStateID(Lib.getInstance().actionDecoder_Target(nextMove));
				targetList.add(target1);
				int select_id =  Lib.getInstance().actionDecoder_SELECT(nextMove);
				combat(Action,role, targetList,select_id);
				role.state.round++;
				notifyActionTimer();
			}
			
		}
		if(allDied)
			battleEnd =true;
	}
	private void poseActionTimer(){
		battleTimer = false;
	}
	private void notifyActionTimer(){
		battleTimer = true;
	}
	
}

//if(r.getSkills()!=null){
//for(Skill skill : r.getSkills()){
//	float rnd = new Random().nextFloat();
//	if(skill.triggerChance>rnd){
//		if(skill.selectType == Define.SKILL_SINGLE){
//			SkillAction skillAction = new SkillAction(skill);
//			skillAction.doAction(source, r, Define.DO_ALL_ACTION);
//		}
//	}
//}
//}
