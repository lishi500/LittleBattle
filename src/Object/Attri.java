package Object;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import Common.Define;

public class Attri implements Serializable{
	public int id;
	final String name;
	int DefineID;
	final int type;
	float floatValue;
	int intValue;
	String stringValue;
	boolean booleanValue;
	
	public Attri(int id, String name, int DefineID, int type, Object value){
		this.id = id;
		this.name = name;
		this.DefineID = DefineID;
		this.type = type;
		setValue(value);
	}
	public Attri(String name, int DefineID, int type, Object value){
		this.name = name;
		this.DefineID = DefineID;
		this.type = type;
		setValue(value);
	}
	
	public void setValue(Object value){
		switch (type) {
		case Define.INT_TYPE:
			intValue = (Integer)value;
			break;
		case Define.FLOAT_TYPE:
			floatValue = (Float)value;	
			break;
		case Define.STRINF_TYPE:
			stringValue = (String)value;
			break;
		case Define.BOOLEAN_TYPE:
			booleanValue = (Boolean)value;
			break;
		default:
			break;
		}
	}
	public Object getValue(){
		switch (type) {
		case Define.INT_TYPE:
			return intValue ;
		case Define.FLOAT_TYPE:
			return floatValue;	
		case Define.STRINF_TYPE:
			return stringValue;
		case Define.BOOLEAN_TYPE:
			return booleanValue;
		default:
			return null;
		}
	}
	public void addValue(Attri attri){
		switch(attri.type){
		case Define.INT_TYPE:
			setValue(this.getInt()+attri.getInt());
		case Define.FLOAT_TYPE:
			setValue(this.getFloat()+attri.getFloat());
		}
	}
	public void dropValue(Attri attri){
		switch(attri.type){
		case Define.INT_TYPE:
			setValue(this.getInt()-attri.getInt());
		case Define.FLOAT_TYPE:
			setValue(this.getFloat()-attri.getFloat());
		}
	}
	
	public float getFloat(){return floatValue;}
	public int getInt(){return intValue;}
	public String getString(){return stringValue;}
	public boolean getBoolean(){return booleanValue;}
	public String getValueInString(){
		switch (type) {
		case Define.INT_TYPE:
			return String.valueOf(intValue);
		case Define.FLOAT_TYPE:
			return String.valueOf(floatValue);
		case Define.STRINF_TYPE:
			return stringValue;
		case Define.BOOLEAN_TYPE:
			return String.valueOf(booleanValue);
		default:
			break;
		}
		return null;
	}
	public void setValueWithString(String value){
		switch (type) { 
		case Define.INT_TYPE:
			intValue =  Integer.valueOf(value);
			break;
		case Define.FLOAT_TYPE:
			floatValue = Float.valueOf(value);
			break;
		case Define.STRINF_TYPE:
			stringValue = value;
			break;
		case Define.BOOLEAN_TYPE:
			booleanValue =  Boolean.valueOf(value);
			break;
		default:
			break;
		}
	}
	
	@Override
	public String toString() {
		return "Attri [name=" + name + ", DefineID=" + DefineID + ", type=" + type
				+ ", floatValue=" + floatValue + ", intValue=" + intValue
				+ ", stringValue=" + stringValue + ", booleanValue="
				+ booleanValue + "id" + id +"]";
	}

	public String getName() {
		return name;
	} 

	public int getDefineID() {
		return DefineID;
	}
	public void setDefineID(int DefineID) {
		this.DefineID = DefineID;
	}
	public int getType() {
		return type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@SuppressWarnings("unchecked")
	public static Attri cloneTo(Attri src) throws RuntimeException {
		ByteArrayOutputStream memoryBuffer = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		Attri dist = null;
		try {
			out = new ObjectOutputStream(memoryBuffer);
			out.writeObject(src);
			out.flush();
			in = new ObjectInputStream(new ByteArrayInputStream(memoryBuffer.toByteArray()));
			dist = (Attri) in.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (out != null)
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			if (in != null)
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
		return dist;
	}
}
