package Object;

import java.io.File;
import java.lang.reflect.Field;

public abstract class LevelGrowth {
	public LevelGrowth(){}
	
	public LevelGrowth(float hP_MAX_UP, float mana_MAX_UP, float aD_UP,
			float mP_UP, float armor_UP, float mR_UP,float fraction) {
		super();
		HP_MAX_UP = hP_MAX_UP;
		Mana_MAX_UP = mana_MAX_UP;
		AD_UP = aD_UP;
		MP_UP = mP_UP;
		Armor_UP = armor_UP;
		MR_UP = mR_UP;
		this.fraction = fraction;
	}
	 float HP_MAX_UP;
	 float Mana_MAX_UP;
	 float AD_UP;
	 float MP_UP;
	 float Armor_UP;
	 float MR_UP; 
	 float fraction;
	
	public void levelUp(Role roll){
		Field[] fields = this.getClass().getSuperclass().getDeclaredFields();
		RoleAttributes attributes = roll.getAttribute();
		for(Field f : fields){
			Attri attri;
			try {
				attri = (Attri)f.get(this);
				attributes.addAttriValue(attri);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setInitialAttri(Attri attri) {
		Object obj = this;
		String name = attri.getName(); 
		try {
			
			Field f = obj.getClass().getSuperclass().getDeclaredField(name);
			f.setAccessible(true);  
			f.set(obj, attri);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
