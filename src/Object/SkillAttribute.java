package Object;

public class SkillAttribute {
	public SkillAttribute(){}
	public SkillAttribute(float base, float scale, int valueType,
			int damageType, int scaleType) {
		super();
		this.base = base;
		this.scale = scale;
		this.valueType = valueType;
		this.damageType = damageType;
		this.scaleType = scaleType;
	}
	public SkillAttribute(int id, float base, float scale, int valueType,
			int damageType, int scaleType) {
		super();
		this.id = id;
		this.base = base;
		this.scale = scale;
		this.valueType = valueType;
		this.damageType = damageType;
		this.scaleType = scaleType;
	}
	public int id;
	public float base;
	public float scale;
    public int valueType;//damage,heal,mana,damagePercent,healPerc,manaPrec,stun,sleep,mute,immue
    public int damageType;
    public int  scaleType;
	@Override
	public String toString() {
		return "SkillAttribute [id=" + id + ", base=" + base + ", scale="
				+ scale + ", valueType=" + valueType + ", damageType="
				+ damageType + ", scaleType=" + scaleType + "]";
	}
}
