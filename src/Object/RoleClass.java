package Object;

public abstract class RoleClass {
	public RoleClass(){}
	public RoleClass(String name,int id,int class_id) {
		super();
		this.id = id;
		this.name = name;
		this.class_id = class_id;
	}
	public int id,class_id;
	public String name;
	public LevelGrowth growth;
	public RoleAttributes initial;
	public int AttactRange;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LevelGrowth getGrowth() {
		return growth;
	}
	public void setGrowth(LevelGrowth growth) {
		this.growth = growth;
	}
	public RoleAttributes getInitial() {
		return initial;
	}
	public void setInitial(RoleAttributes initial) {
		this.initial = initial;
	}
	@Override
	public String toString() {
		return name+":["+class_id+"]";
	}
	
}
