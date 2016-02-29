package Battle;

import java.util.ArrayList;
import java.util.Random;

import Common.Define;
import Object.Role;
import Object.Skill;

public class SkillAction extends Action{
	private Skill skill;
	public SkillAction(Skill skill){this.skill=skill;}
	public void setSkill(Skill skill){this.skill = skill;}

	@Override
	void preAction(Role source, Role target, int identifier) {
		if(act(identifier, Define.PRE_ACTION)){
			if(!target.getAttribute().isAlive()){
				identifier = Define.DO_PRE_AFTER_ACTION;
			}
			//System.err.println("Ready Pre trigger");
			if(skill.triggerType>0){
				Random random = new Random();
				if(skill.triggerChance<random.nextFloat())
					this.identifier =  Define.DO_PRE_AFTER_ACTION;
			}
			BattleState state = source.state;
			state.state = Define.ACTION;
		}
	}

	@Override
	void inAction(Role source, Role target, int identifier) {
		if(act(identifier, Define.IN_ACTION)){
			System.err.println("Skill casted");
			skill.castSingle(source, target);
		}
	}

	@Override
	void afterAction(Role source, Role target, int identifier) {
		if(act(identifier, Define.AFTER_ACTION)){
			BattleState state = source.state;
			state.state = Define.WAIT;
			if(!target.getAttribute().isAlive()){
				System.out.println(target.name + " died, " + source.name + " WIN! ");
			}
		}
	}

}
