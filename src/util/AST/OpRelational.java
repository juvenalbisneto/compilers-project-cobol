package util.AST;

import checker.SemanticException;

public class OpRelational extends Terminal{
	String opBool;
	
	public OpRelational(String spelling) {
		super(spelling);
	}

	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitOpRelational(this, args);
	}
}
