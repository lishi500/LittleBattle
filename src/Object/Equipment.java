package Object;

import java.io.NotActiveException;
import java.util.Hashtable;

public abstract class Equipment {
	int id;
	Role role;
	Gear HEAD;
	Gear CHEST;
	Gear HANDS;
	Gear LEG;
	Gear BOOTS;
	Gear WEAPON_L;
	Gear WEAPON_R;
	Gear RING; 
	
	public Equipment(Role role){
		this.role = role;
	}
	public Gear getBySlot(int slot){
		if(equipments.containsKey(Integer.valueOf(slot))){
			return equipments.get(slot);
		}else{
			System.out.println("Not Contain : "  + slot);
		}
		return null;
	}
	Hashtable<Integer, Gear> equipments = new Hashtable<Integer, Gear>();
	
	public void equip(Role role, Gear gear){
		if(equipments.containsKey(gear.SLOT)){
			un_equip(role, equipments.get(gear.SLOT));
			gear.equip(role);
			equipments.put(gear.SLOT, gear);
		}else{
			gear.equip(role);
			equipments.put(gear.SLOT, gear);
		}
	}
	public void un_equip(Role role, Gear gear){
		if(equipments.containsKey(gear.SLOT)){
			gear.un_equip(role); 
			equipments.remove(gear.SLOT);
		}
	}
	public void toPrint(){
		System.out.println("------------ to Print --------- size: " + equipments.size());
		for(Integer i: equipments.keySet()){
			System.out.println(equipments.get(i).toString());
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Gear getHEAD() {
		return HEAD;
	}
	public void setHEAD(Gear hEAD) {
		HEAD = hEAD;
	}
	public Gear getCHEST() {
		return CHEST;
	}
	public void setCHEST(Gear cHEST) {
		CHEST = cHEST;
	}
	public Gear getHANDS() {
		return HANDS;
	}
	public void setHANDS(Gear hANDS) {
		HANDS = hANDS;
	}
	public Gear getLEG() {
		return LEG;
	}
	public void setLEG(Gear lEG) {
		LEG = lEG;
	}
	public Gear getBOOTS() {
		return BOOTS;
	}
	public void setBOOTS(Gear bOOTS) {
		BOOTS = bOOTS;
	}
	public Gear getWEAPON_L() {
		return WEAPON_L;
	}
	public void setWEAPON_L(Gear wEAPON_L) {
		WEAPON_L = wEAPON_L;
	}
	public Gear getWEAPON_R() {
		return WEAPON_R;
	}
	public void setWEAPON_R(Gear wEAPON_R) {
		WEAPON_R = wEAPON_R;
	}
	public Gear getRING() {
		return RING;
	}
	public void setRING(Gear rING) {
		RING = rING;
	}
	public Hashtable<Integer, Gear> getEquipments() {
		return equipments;
	}
	public void setEquipments(Hashtable<Integer, Gear> equipments) {
		this.equipments = equipments;
	}
}
