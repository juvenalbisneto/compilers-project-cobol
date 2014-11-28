package util.AST;

import checker.SemanticException;

public class Return extends Command {
	Identifier id = null;
	Expression exp = null;

	public Return(Identifier id){
		this.id = id;
	}
	public Return(Expression exp){
		this.exp = exp;
	}	
	
	public Identifier getIdentifier(){
		return this.id;
	}
	public void setIdentifier(Identifier id){
		this.id = id;
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
		return v.visitReturn(this, args);
	}
}