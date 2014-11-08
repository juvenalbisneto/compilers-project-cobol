package util.AST;

import checker.SemanticException;

public class Continue extends Command {

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitContinue(this, args);
	}
}
