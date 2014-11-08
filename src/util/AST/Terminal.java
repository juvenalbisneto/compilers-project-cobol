package util.AST;

import checker.SemanticException;

public class Terminal extends AST {
	String token = "Terminal";
	public String spelling;
	
	public Terminal(String spelling){
		this.spelling = spelling;
	}

	@Override
	public String toString(int level) {
		return (super.getSpaces(level) + token);
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return null;
	}
}
