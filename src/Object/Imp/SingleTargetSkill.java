package Object.Imp;

import java.awt.geom.FlatteningPathIterator;
import java.util.ArrayList;

import Battle.BasicBattle;
import Common.BattleLib;
import Common.Define;
import Object.Role;
import Object.Skill;
import Object.SkillAttribute;

public class SingleTargetSkill extends Skill{
	public SingleTargetSkill(){}
	public SingleTargetSkill(int id, int level, String name, int selectType,
			int targetType, float cooldown, float manacost, String description,
			int area, float triggerChance,int triggerType) {
		super(id, level, name, selectType, targetType, cooldown, manacost, description, area, triggerChance,triggerType);
	}
	@Override
	public void cast(Role source, ArrayList<Role> targets) {
		
	}

	@Override
	public void castSingle(Role source, Role target){
		if(source.getAttribute().getMana()<this.manacost || this.to_cooldown>0){
			if(source.getAttribute().getMana()<this.manacost)
				System.out.println(source.name + " Has insufficient mana to cast " + name);
			if(this.to_cooldown>0)
				System.out.println("Skill " + name +" are in COOLDOWN, available after: " + to_cooldown +" round");
			return;
		}
		System.out.print(source.name +" Cast Spell [" + name + "] to " + target.name+ ", ");
		source.getAttribute().consumeMana(this.manacost);
		to_cooldown = cooldown;
		applyEffect(source, target);
		
		for(SkillAttribute sa : this.attributeList){
			switch (sa.damageType) {
			case Define.DAMAGE_SKILL_ATTRIBUTE:
				float apply_damage = sa.base + BattleLib.getInstance().getScaledValue(source, sa.scale, sa.scaleType);
				float actual_damage = getDamageAfterDefince(apply_damage, sa.damageType, source, target);
				actual_damage = target.state.trigger(Define.PHY_ATTACK_ACTION, actual_damage, source, target);
				target.getAttribute().damage(actual_damage);
				System.out.println(" deal " + actual_damage + " " + Define.DAMAGE_TYPE[sa.damageType] +" damage("+sa.base  + "+" +BattleLib.getInstance().getScaledValue(source,sa.scale,sa.scaleType) + "), drop " + target.name +" to " + target.getAttribute().getHP()+"\n");
				break;
			case Define.DAMAGE_PERC_SKILL_ATTRIBUTE:
				
				break;
				
			default:
				break;
			}
		}
		
//		if(damageBase>0){
//			if(source.state.group!=target.state.group){
//				float apply_damage = damageBase + BattleLib.getInstance().getScaledValue(source,damageScale,damageScaleType);
//				float actual_damage = getDamageAfterDefince(apply_damage, damageType, source, target);
//				actual_damage = target.state.trigger(Define.PHY_ATTACK_ACTION, actual_damage, source, target);
//				target.getAttribute().damage(actual_damage);
//				System.out.println(" deal " + actual_damage + " " + Define.DAMAGE_TYPE[damageType] +" damage("+damageBase  + "+" +BattleLib.getInstance().getScaledValue(source,damageScale,damageScaleType) + "), drop " + target.name +" to " + target.getAttribute().getHP());
//			}
//		}
//			
//		if(healBase>0){
//			if(source.state.group==target.state.group){
//				float heal_amount = healBase + BattleLib.getInstance().getScaledValue(source, healScale,healScaleType);
//				heal_amount = target.state.trigger(Define.HEAL_ACTION, heal_amount, source, target);
//				target.getAttribute().heal(heal_amount);
//				System.out.println(" heal " + heal_amount + " hp");
//			}
//		}
	}
	

}
	

