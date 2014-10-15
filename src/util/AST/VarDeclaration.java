package util.AST;

public abstract class VarDeclaration extends AST {
	int local;
	String type;
	Identifier id;
	
	public int getLocal(){
		return this.local;
	}
	public String getType(){
		return this.type;
	}
	public Identifier getIdentifier(){
		return this.id;
	}
}