package util.AST;

import checker.SemanticException;

public class Display extends Command {
	Identifier id = null;
	Expression exp = null;

	public Display(Identifier id){
		this.id = id;
	}
	public Display(Expression exp){
		this.exp = exp;
	}

	public Identifier getIdentifier(){
		return this.id;
	}
	public Expression getExpression(){
		return this.exp;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitDisplay(this, args);
	}
}