package util.AST;

public class VarPIC9Declaration extends VarDeclaration {
	Number num = null;

	public VarPIC9Declaration(int local, String type, Identifier id){
		this.local = local;
		this.type = type;
		this.id = id;
	}
	public VarPIC9Declaration(int local, String type, Identifier id, Number num){
		this.local = local;
		this.type = type;
		this.id = id;
		this.num = num;
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