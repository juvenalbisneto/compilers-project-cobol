package util.AST;

public class VarPICBOOLDeclaration extends VarDeclaration {
	
	BoolValue bool = null;

	public VarPICBOOLDeclaration(int local, String type, Identifier id){
		this.local = local;
		this.type = type;
		this.id = id;
	}
	public VarPICBOOLDeclaration(int local, String type, Identifier id, BoolValue bool){
		this.local = local;
		this.type = type;
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