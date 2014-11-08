package util.AST;

import checker.SemanticException;

public class OpAdd extends Terminal {
	public OpAdd(String spelling) {
		super(spelling);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitOpAdd(this, args);
	}
}
