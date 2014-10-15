package util.AST;

public class VarPICBOOLDeclaration extends VarDeclaration {
	
	BoolValue bool = null;

	public VarPICBOOLDeclaration(int local, Identifier id){
		this.type = "PICBOOL";
		this.id = id;
	}
	public VarPICBOOLDeclaration(int local, Identifier id, BoolValue bool){
		this.type = "PICBOOL";
		this.id = id;
		this.bool = bool;
	}

	public BoolValue getBoolValue(){
		return this.bool;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}