package util.AST;

public class VarPICBOOLDeclaration extends VarDeclaration {
	BoolValue bool = null;

	public VarPICBOOLDeclaration(Identifier id){
		super("PICBOOL");
		this.id = id;
	}
	public VarPICBOOLDeclaration(Identifier id, BoolValue bool){
		super("PICBOOL");
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