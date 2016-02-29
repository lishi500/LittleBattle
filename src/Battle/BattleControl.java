package Battle;

import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

import org.omg.CORBA.PRIVATE_MEMBER;

import Common.Define;
import Common.Lib;
import Object.Role;
import Object.Skill;

public class BattleControl {
	private Role role;
	private ArrayList<Role> alies;
	private ArrayList<Role> enemies;
	Scanner user_input;
	int scanner_state;
	int action_code;
	public BattleControl(Role role, ArrayList<Role> alies, ArrayList<Role> enemies) {
		// TODO Auto-generated constructor stub
		this.role = role;
		this.alies = alies;
		this.enemies = enemies;
		scanner_state = 0;
		action_code=0;
	}
	public int AskForAction() {
		user_input = new Scanner( System.in );
		return ActionSelectedSwith();
	}
	
	public int ActionSelectedSwith(){
		String nextAction = null;
		switch (scanner_state) {
		case 0:
			System.out.println(ActionStart);
			nextAction = user_input.nextLine().trim();
			if(Lib.getInstance().RegexIsNumber(nextAction)){
				int next_action = Integer.parseInt(nextAction);
				if(next_action>0 && next_action<=5){
					scanner_state = next_action;
					return ActionSelectedSwith();
				}
			}
			break;
		case 1:
			StringBuilder sb = new StringBuilder();
			sb.append(ActionAttack);int count = 1;
			for(Role enemy:enemies){
				sb.append(count++ + ". " + enemy.name);
			}
			System.out.println(sb.toString());
			nextAction = user_input.nextLine().trim();
			if(Lib.getInstance().RegexIsNumber(nextAction)){
				int next_action = Integer.parseInt(nextAction);
				if(next_action>0 && next_action<=enemies.size()){
					System.out.println(role.toString() +" -------> " + enemies.get(next_action-1).toString());
					action_code = actionEncoder(Define.ACTION_ATTACK, role, enemies.get(next_action-1), null,Define.SELECT_NOTHING);
				}
			}
			break;
		case 2:	
			StringBuilder sb2 = new StringBuilder();
			sb2.append(ActionSkillSelect);int count2=1;
			
			Map<Integer, Skill> skillMap = new Hashtable<Integer, Skill>();
			for(Skill s:role.getSkills()){
				if(s.selectType != Define.SELECT_PASSIVE){
					skillMap.put(count2, s);
					sb2.append(count2++ + "."+s.name +" ");
				}
			}
			System.out.println(sb2.toString());
			nextAction = user_input.nextLine().trim();
			if(!Lib.getInstance().RegexIsNumber(nextAction))
				return ActionSelectedSwith();
			Skill selectSkill = skillMap.get(Integer.parseInt(nextAction));
			System.out.println(selectSkill.toString());
			if(selectSkill!=null){
				sb2.setLength(0);
				sb2.append(ActionSkillUse);count2 = 1;
				for(Role enemy:enemies){
					sb2.append(count2++ + ". " + enemy.name);
				}
				System.out.println(sb2.toString());
				nextAction = user_input.nextLine().trim();
				if(Lib.getInstance().RegexIsNumber(nextAction)){
					int next_action = Integer.parseInt(nextAction);
					if(next_action>0 && next_action<=enemies.size()){
						action_code = actionEncoder(Define.ACTION_SKILL, role, enemies.get(next_action-1), null,selectSkill.id);
					}
				}
			
			}
			
		break;
		case 3:
			System.out.println("Item");
			break;
		case 4:
			System.out.println("Defence");
			break;
		case 5:
			System.out.println("Skip");
			break;
		case 6:
			System.out.println("Run Away");
			break;
		default:
			System.out.println("Invide Input please Input again");
			ActionSelectedSwith();
		}
		return action_code;
	}
	
//	private int actionEncoder(int action_code, Role source, Role target,Role target2 , int Action){
//		if(source!=null){
//			action_code = (action_code)|(source.state.ID<<4);
//		//	System.out.println("EN_Source: "+(source.state.ID<<4));
//		}
//		if(target!=null){
//			action_code = (action_code) | (target.state.ID<<8);
//			//System.out.println("EN_target1: "+(target.state.ID<<8));
//			//System.out.println("Target1::"+target.state.ID);
//		}
//		if(target2!=null){
//			action_code = (action_code) | (target2.state.ID<<12);
//			//System.out.println("En_target2: "+(target2.state.ID<<12));
//		}
//		if(Action!=0){
//			action_code=(action_code)| (Action);
//			//System.out.println("EN_Action: "+(Action));
//		}
//		return action_code;
//	}
	private int actionEncoder(int Action, Role source, Role target,Role target2,  int SelectedID){
		int action_code = 0;
		if(Action!=0){
			action_code=(action_code)| (Action);
			//System.out.println("EN_Action: "+(Action));
		}
		if(source!=null){
			action_code = (action_code)|(source.state.ID<<4);
		//	System.out.println("EN_Source: "+(source.state.ID<<4));
		}
		if(target!=null){
			action_code = (action_code) | (target.state.ID<<8);
			//System.out.println("EN_target1: "+(target.state.ID<<8));
			//System.out.println("Target1::"+target.state.ID);
		}
		if(target2!=null){
			action_code = (action_code) | (target2.state.ID<<12);
			//System.out.println("En_target2: "+(target2.state.ID<<12));
		}
		if(SelectedID!=Define.SELECT_NOTHING){
			action_code = (action_code) | (SelectedID<<16);
			//System.out.println("En_target2: "+(target2.state.ID<<12));
		}
		
		return action_code;
	}
	final String ActionStart = "What action you want to do next? 1. Attack 	2.Use Skill 3.Defence  4.Await orders 5.Run away";
	final String ActionAttack = "Select target role you want to attack.";
	final String ActionSkillSelect ="Select a skill you want to cast.";
	final String ActionSkillUse ="Select target you want to cast.";
}
