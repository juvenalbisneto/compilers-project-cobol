package util.AST;

public class VarPIC9Declaration extends VarDeclaration {
	Number num = null;

	public VarPIC9Declaration(Identifier id){
		this.type = "PIC9";
		this.id = id;
	}
	public VarPIC9Declaration(Identifier id, Number num){
		this.type = "PIC9";
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