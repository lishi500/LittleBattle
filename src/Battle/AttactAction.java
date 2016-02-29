package Battle;

import java.util.ArrayList;

import Common.BattleLib;
import Common.Define;
import Object.Role;
import Object.Skill;

public class AttactAction extends Action{


	@Override
	void preAction(Role source, Role target,int identifier) {
		if(act(identifier, Define.PRE_ACTION)){
			if(!target.getAttribute().isAlive()){
				identifier = Define.DO_PRE_AFTER_ACTION;
			}
			BattleState state = source.state;
			state.state = Define.ACTION;
		}
	}

	@Override
	void inAction(Role source, Role target,int identifier) {
		if(BattleLib.getInstance().simple_distance(source, target)>source.getRollClass().AttactRange){
			System.out.println("Too far away, cannot reach");
			return;
		}
		for(Skill skill:source.getSkills()){
			if(skill.triggered(Define.TRIGGER_ATTACK)){
				if(skill.selectType==Define.SELECT_SELF){
					SkillAction skillAction = new SkillAction(skill); 
					skillAction.doAction(source, source, identifier);
				}
				else{
					SkillAction skillAction = new SkillAction(skill); 
					skillAction.doAction(source, target, identifier);
					System.err.println("Skill:[" +skill.name + "] source:"+source.toString() + " target "+target.toString() );
				}
			}
		}
		if(act(identifier, Define.IN_ACTION)){
			float attack = source.getAttribute().getByFieldName("AD").getFloat();
			float armor = target.getAttribute().getByFieldName("Armor").getFloat();
			float damage = BattleLib.getInstance().DamageCalculate(attack, armor);
			target.getAttribute().damage(damage);
			System.out.println(source.name + " Attack " + target.name + " deal " + Math.round(damage) + " damage, drop " + target.name +" to " + target.getAttribute().getHP()+"\n");
		}
	}

	@Override
	void afterAction(Role source, Role target,int identifier) {
		if(act(identifier, Define.AFTER_ACTION)){
			BattleState state = source.state;
			state.state = Define.WAIT;
			if(!target.getAttribute().isAlive()){
				System.out.println(target.name + " died, " + source.name + " WIN! ");
			}
		}
	}



}
