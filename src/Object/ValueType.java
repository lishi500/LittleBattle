package Object;
 
public class ValueType {
	public ValueType(String typeName, int type) {
		super();
		this.typeName = typeName;
		this.type = type;
	}
	String typeName;
	int type;
	@Override
	public String toString() {
		return typeName + " [" + type + "]";
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
