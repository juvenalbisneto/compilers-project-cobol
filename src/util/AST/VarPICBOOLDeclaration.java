package util.AST;

public class VarPICBOOLDeclaration extends VarDeclaration {
	int local;
	String type;
	Identifier id;
	Number num = null;

	public VarPICBOOLDeclaration(int local, String type, Identifier id){
		this.local = local;
		this.type = type;
		this.id = id;
	}
	public VarPICBOOLDeclaration(int local, String type, Identifier id, Number num){
		this.local = local;
		this.type = type;
		this.id = id;
		this.num = num;
	}

	public int getLocal(){
		return this.local;
	}
	public String getType(){
		return this.type;
	}
	public Identifier getIdentifier(){
		return this.id;
	}
	public Number getNumber(){
		return this.num;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}