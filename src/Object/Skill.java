package Object;

import java.util.ArrayList;
import java.util.HashSet;

import Common.BattleLib;
import Common.Define;

public abstract class Skill {

	public Skill(int id, int level, String name, int selectType,
			int targetType, float cooldown, float manacost, String description,
			int area, float triggerChance,int triggerType) {
		super();
		this.id = id;
		this.level = level;
		this.name = name;
		this.selectType = selectType;
		this.targetType = targetType;
		this.cooldown = cooldown;
		this.manacost = manacost;
		this.description = description; 
		this.area = area;
		this.triggerChance = triggerChance;
		this.triggerType = triggerType;
		effacts = new HashSet<Effact>();
		attributeList = new  ArrayList<SkillAttribute>();
		to_cooldown=0;
	}
	public Skill(){
		effacts = new HashSet<Effact>();
		attributeList = new  ArrayList<SkillAttribute>();
		to_cooldown=0;
	}
	public void setAttributeList(ArrayList<SkillAttribute> list){
		attributeList = list;
		System.out.println("Set list");
	}
	public void setEffectSet(HashSet<Effact> set){
		effacts = set;
	}
	
	public HashSet<Effact> effacts;
	public ArrayList<SkillAttribute> attributeList;
	public int id;
	public int level;
	public String name;
	public int selectType;//select
	public int targetType;
	public float cooldown;
	public float to_cooldown;
	public float manacost;
	public String description;
	
	public int range;
	public int area;
	public float triggerChance;
	public int triggerType;
	
	public int SELECT;
	
	
	public abstract void cast(Role source, ArrayList<Role> targets);
	public abstract void castSingle(Role source, Role target);
	public void applyEffect(Role source, Role target){
		
	}
	public boolean triggered(int TriggerMask){
		if((triggerType&TriggerMask)>0)
			return true;
		return false;
	}
	public void cooldown(){
		if(to_cooldown>0){
			to_cooldown--;
		}
	}
	protected float getDamageAfterDefince(float apply_damage, int damageType, Role source, Role targets){
		float actual_damage = 0;
		switch(damageType){
		case Define.PHYSICAL_DAMAGE:
			actual_damage = BattleLib.getInstance().DamageCalculate(apply_damage, targets.getAttribute().getByFieldName(Define.ATTRI_NAME[Define.Armor]).getFloat());
			break;
		case Define.MAGIC_DAMAGE:
			actual_damage = BattleLib.getInstance().DamageCalculate(apply_damage, targets.getAttribute().getByFieldName(Define.ATTRI_NAME[Define.MR]).getFloat());
			break;
		case Define.TRUE_DAMAGE:
			actual_damage = apply_damage;
			break;
		}
		return actual_damage;
	}
	@Override
	public String toString() {
		return "Skill [id=" + id + ", level=" + level + ", name=" + name
				+ ", selectType=" + selectType + ", targetType=" + targetType
				+ ", cooldown=" + cooldown + ", to_cooldown=" + to_cooldown
				+ ", manacost=" + manacost + ", description=" + description
				+ ", range=" + range + ", area=" + area + ", triggerChance="
				+ triggerChance + "]";
	}
	public void toPrint(){
		for(SkillAttribute saAttribute : attributeList)
			System.out.println(saAttribute.toString());
		for(Effact effact : effacts){
			System.out.println(effact.toString());
		}
	}
}
