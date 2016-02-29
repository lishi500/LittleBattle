package Object;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import Common.Define;

public abstract class RoleAttributes {
	protected Attri HP;
	protected Attri HP_MAX;
	protected Attri Mana;
	protected Attri Mana_MAX;
	protected Attri EXP;
	protected Attri Level;
	protected Attri AD;
	protected Attri AP;
	protected Attri Armor;
	protected Attri MR;
	protected Attri alive;
	protected Attri Money;
	protected Attri RoleName;
	protected Attri Speed;
	  
	public RoleAttributes(){
		
	}
	public abstract void InitialAttributes();
	
	public float getHP() {
		return HP.getFloat();
	}
	public void damage(float hp){
		setHP(this.HP.getFloat() - hp);
	}
	public void heal(float hp){
		if(alive.getBoolean()){
			if(this.HP.getFloat()+hp>this.HP_MAX.getFloat())
				this.HP = this.HP_MAX;
			this.HP.setValue(this.HP.getFloat()+hp);
		}
	}
	private void setHP(float hP) {
		HP.setValue(hP);
		if(HP.getFloat()<=0){
			HP.setValue(0.0f);
			setAlive(false);
		}
	}
	public float getMana() {
		return Mana.getFloat();
	}
	public void regerenateMana(float mana){
		if(alive.getBoolean()){
			if(this.Mana.getFloat()+mana>this.Mana_MAX.getFloat())
				this.Mana.setValue(this.Mana_MAX.getFloat());
			this.Mana.setValue(this.Mana.getFloat()+mana);
		}
	}
	public boolean consumeMana(float mana){
		if(this.Mana.getFloat() > mana){
			setMana(this.Mana.getFloat() - mana);
			return true;
		}
		return false;	
	}
	private void setMana(float mana) {
		Mana.setValue(mana);
		if(Mana.getFloat()<=0)
			mana=0;
	}
	
	private void gainEXP(int exp){
		setEXP(this.EXP.getInt()+exp);
	}
	private void setEXP(int eXP) {
		EXP.setValue( eXP);
		if(this.EXP.getInt() > Define.LEVELS[this.Level.getInt()]){
			this.EXP.setValue(this.EXP.getInt()-Define.LEVELS[this.Level.getInt()]);
			setLevel(getLevel()+1);
		}
	}
	public int getLevel() {
		return Level.getInt();
	}
	private void setLevel(int level) {
		Level.setValue(level);
	}
	public void resurrect(){
		resurrect(this.HP_MAX.getFloat()*0.2f, this.Mana_MAX.getFloat()*0.2f);
	}
	public void resurrect(float hp, float mana){
		setAlive(true);
		heal(hp);
		regerenateMana(mana);
	}
	public boolean isAlive() {
		return alive.getBoolean();
	}
	private void setAlive(boolean alive) {
		this.alive.setValue(alive);
	}
	public float getMoney(){
		return Money.getFloat();
	}
	public void gainMoney(float amount){
		Money.setValue(Money.getFloat()+amount);
	}
	public boolean consumeMoney(float amount){
		if(amount>Money.getFloat()){
			return false;
		}else{
			Money.setValue(Money.getFloat()-amount);
			return true;
		}
	}
	public float getHP_MAX(){
		return HP_MAX.getFloat();
	}
	public float getMana_MAX(){
		return Mana_MAX.getFloat();
	}
	
	
	public Attri getByFieldName(String name){
		Attri attri = null;
		try {
			attri = (Attri) this.getClass().getSuperclass().getDeclaredField(name).get(this);
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return attri;
	}
	
	public Attri generateAttri(Node node) throws Exception{
		Attri attri = null;
		
		String valueObject = node.getTextContent();
		String name = node.getAttributes().getNamedItem("name").getTextContent();
		
		Object object = Define.getInstance();
		Field f = object.getClass().getDeclaredField(name);
		int id = f.getInt(object);
		int type = Define.ATTRI_TYPE[id];

		switch (type) {
		case Define.INT_TYPE:
			attri = new Attri(name, id, type, Integer.valueOf(valueObject));
			break;
		case Define.FLOAT_TYPE:
			attri = new Attri(name, id, type, Float.valueOf(valueObject));
			break;
		case Define.STRINF_TYPE:
			attri = new Attri(name, id, type, valueObject);
			break;
		case Define.BOOLEAN_TYPE:
			attri = new Attri(name, id, type, Boolean.valueOf(valueObject));
			break;
		default:
			break;
		}
		return attri;
	}
	public void addAttriValue(Attri attri){
		String name = attri.getName();
		try {
			Field f = this.getClass().getSuperclass().getDeclaredField(name);
			Attri curr = (Attri) f.get(this);
			curr.addValue(attri);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		if(name.equals("HP_MAX")){
			heal(attri.getFloat());
		}else if(name.equals("Mana_MAX")){
			regerenateMana(attri.getFloat());
		}
	}
	public void dropAttriValue(Attri attri){
		String name = attri.getName();
		try {
			Field f = this.getClass().getSuperclass().getDeclaredField(name);
			Attri curr = (Attri) f.get(Attri.class);
			curr.dropValue(attri);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		if(name.equals("HP_MAX")){
			if(getHP()>getHP_MAX())
				setHP(getHP_MAX());
		}else if(name.equals("Mana_MAX")){
			if(getMana()>getMana_MAX())
				setMana(getMana_MAX());
		}
	}
	
	public abstract void setInitialAttri(Attri attri);
	public ArrayList<Attri> getAllAttributes() throws IllegalArgumentException, IllegalAccessException{
		ArrayList<Attri> list = new ArrayList<Attri>();
		Field[] f_super = this.getClass().getSuperclass().getDeclaredFields();
		Field[] f_local = this.getClass().getDeclaredFields();
		for(Field f : f_super){
			if(f.get(this) instanceof Attri){
				Attri curr = (Attri) f.get(this);
				if(curr!=null)
					list.add((Attri)f.get(this));
			}
		}
//		for(Field f : f_local){
//			if(f.get(this) instanceof Attri){
//				Attri curr = (Attri) f.get(this);
//				if(curr!=null)
//					list.add((Attri)f.get(this));
//			}
//		}
		return list;
	}
	
	@Override
	public String toString() {
		return "RoleAttributes [HP=" + HP.toString() +  ", Mana="
				+ Mana.toString() +", AD=" + AD.toString() + ", AP=" + AP.toString() + ", Armor=" + Armor.toString()+ ", Speed=" + Speed.toString()
				+ ", MR=" + MR.toString() +", HP_MAX=" + HP_MAX.toString() +  ", Mana_MAX=" + Mana_MAX.toString()+ ", alive=" + alive.toString() 
				+ ", EXP=" + EXP.toString() + ", Level="
				+ Level.toString() +"]";
	}
}
