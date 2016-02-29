package Object;

public abstract class Effact {
	public int id;
	public String name;
	public int type;
	public int trigger;
	
	public int duartion;
	public int durationLeft;
	public float durationDecayFactor;
	public boolean overlap;
	public boolean isbuff;

	public abstract void apply_effect(Skill skill, Role source, Role target);
	public abstract int trigger(int ActionCode);
	public abstract float react(int reaction, float value, Role source, Role target);
	public void durationEffect(Skill skill, Role source, Role target){
		if(durationLeft>0){
			apply_effect(skill, source, target);
		}
		durationLeft--;
	}
	@Override
	public String toString() {
		return "Effact [id=" + id + ", name=" + name + ", type=" + type
				+ ", trigger=" + trigger + ", duartion=" + duartion
				+ ", durationLeft=" + durationLeft + ", durationDecayFactor="
				+ durationDecayFactor + ", overlap=" + overlap + ", isbuff="
				+ isbuff + "]";
	}
}
