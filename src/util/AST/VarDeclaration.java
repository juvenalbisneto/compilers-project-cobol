package util.AST;

public abstract class VarDeclaration extends AST {
	String type;
	Identifier id = null;
	
	public VarDeclaration(String type){
		this.type = type;
	}
	
	public String getType(){
		return this.type;
	}
	public Identifier getIdentifier(){
		return this.id;
	}
}