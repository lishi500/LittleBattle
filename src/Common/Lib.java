package Common;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;

import Object.Role;
import Object.Skill;

public class Lib {
	private static Lib lib;
	private Lib(){}
	public static Lib getInstance(){
		if(lib==null){
			synchronized (Lib.class) {
				if(lib==null)
					lib= new Lib();
			}
		}
		return lib;
	}
	
	public void generateBox(ArrayList list, JComboBox selector){
		for(Object object : list){
			selector.addItem(object);
		}
	}
	
	public boolean isNumber(String str){
		if(str.matches("\\d+"))
			return true;
		return false;
	}
	
	public JList reloadList(ArrayList  arraylist,JList list, JPanel container){
		if(arraylist!=null){
			Object[] array = new Object[arraylist.size()];
			int count = 0;
			for(Object obj : arraylist){
				array[count++]=obj;
			}
			container.remove(list);
			list = new JList(array);
			return list;
		}
		return list;
	}
	public Object getStringValueToObject(String value,int type){
		switch (type) {
		case Define.INT_TYPE:
			return new Integer(Integer.valueOf(value));
		case Define.FLOAT_TYPE:
			return new Float(Float.valueOf(value));
		case Define.STRINF_TYPE:
			return value;
		case Define.BOOLEAN_TYPE:
			return new Boolean(Boolean.valueOf(value));
		default:
			break;
		}
		return null;
	}
	public boolean RegexIsNumber(String str){
		return str.matches("\\d+");  
	}
	public int actionDecoder_Action(int encode_action){
		//System.out.println("DE_Action: " + (encode_action&Define.MASK_ACTION_ID));
		return encode_action&Define.MASK_ACTION_ID;
	}
	public int actionDecoder_Source(int encode_action){
		//System.out.println("DE_Source: "+(encode_action&Define.MASK_SOURCE_ROLE_ID>>4) );
		return (encode_action&Define.MASK_SOURCE_ROLE_ID)>>4;
	}
	public int actionDecoder_Target(int encode_action){
		//System.out.println("DE_Target: "+((encode_action&Define.MASK_TARGET_ROLE_ID1)>>8) );
		//System.out.println("Encode:"+ encode_action + " masked:" + (encode_action&Define.MASK_TARGET_ROLE_ID1) );
		return (encode_action&Define.MASK_TARGET_ROLE_ID1)>>8;
	}
	public int actionDecoder_Target2(int encode_action){
		//System.out.println("DE_Target2: "+(encode_action&Define.MASK_TARGET_ROLE_ID1>>12) );
		return (encode_action&Define.MASK_TARGET_ROLE_ID1)>>12;
	}
	public int actionDecoder_SELECT(int encode_action){
		//System.out.println("DE_Target2: "+(encode_action&Define.MASK_TARGET_ROLE_ID1>>12) );
		return (encode_action&Define.MASK_SELECT_ID)>>16;
	}
	public ArrayList<Role> toRoleGroup(Role r){
		ArrayList<Role> group = new ArrayList<Role>();
		group.add(r);
		return group;
	}
	
	public Skill getSkillFromRole(Role r, int skill_id){
		for(Skill skill : r.getSkills()){
			if(skill.id == skill_id)
				return skill;
		}
		return null;
	}
}

