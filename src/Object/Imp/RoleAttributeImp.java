package Object.Imp;

import java.lang.reflect.Field;

import Common.Define;
import Object.Attri;
import Object.RoleAttributes;
 
public class RoleAttributeImp extends RoleAttributes{
	Attri sleepWell;
	@Override
	public void InitialAttributes() {
		// TODO Auto-generated method stub
//		HP_MAX = new Attri("HP_MAX", Define.HP_MAX, Define.FLOAT_TYPE, 1200);
	}

	@Override
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
