package util.AST;

import checker.SemanticException;

public class OpMult extends Terminal {
	public OpMult(String spelling) {
		super(spelling);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitOpMult(this, args);
	}
}
