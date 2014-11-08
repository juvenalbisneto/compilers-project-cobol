package util.AST;

import checker.SemanticException;

public class Number extends Terminal {
	public Number(String spelling) {
		super(spelling);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitNumber(this, args);
	}
}
