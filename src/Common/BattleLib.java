package Common;


import java.util.Random;

import Battle.Action;
import Object.Role;
import Object.Skill;

public class BattleLib {
	private static BattleLib battleLib;
	private BattleLib(){}
	public static BattleLib getInstance(){
		if(battleLib==null){
			synchronized (Lib.class) {
				if(battleLib==null)
					battleLib= new BattleLib();
			}
		}
		return battleLib;
	}
	
	public float DamageCalculate(float attack, float armor){
		float reduce = armor/(100.0f+armor);
		float damage = attack*(1.0f-reduce);
		if(damage>0)
			return damage;
		return 0.0f;
	}
	public float getScaledValue(Role role, float ratio, int extendType){
		if(extendType!=Define.Level_EXTENDTYPE){
			float attri_value = role.getAttribute().getByFieldName(Define.ATTRI_NAME[extendType]).getFloat();
			return ratio*attri_value;
		}else{
			float attri_value = role.getAttribute().getByFieldName(Define.ATTRI_NAME[Define.Level]).getInt();
			return ratio*attri_value;
		}
	}
	public float simple_distance(Role r1, Role r2){
		return Math.max(Math.abs(r1.state.xpos-r2.state.xpos), Math.abs(r1.state.ypos - r2.state.ypos));
	}
	
	public Action GetSkillAction(Skill skill){
		switch (skill.selectType) {
		case Define.SKILL_SINGLE:

			break;
		case Define.SKILL_GROUP:

			break;
		case Define.SKILL_AURA:

			break;
		case Define.SKILL_ENVIRONMENT:

			break;
		case Define.SKILL_ACTIVE:

			break;
		default:
			break;
		}
		return null;
	}

}
