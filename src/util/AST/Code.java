package util.AST;

import checker.SemanticException;

public class Code extends AST {
	String token = "Code";
	
	GlobalDataDiv gbl = null;
	ProgramDiv pgrm = null;


	public Code(GlobalDataDiv gbl, ProgramDiv pgrm){
		this.gbl = gbl;
		this.pgrm = pgrm;
	}
	
	public Code(ProgramDiv pgrm){
		this.pgrm = pgrm;
	}

	public GlobalDataDiv getGlobalDataDiv(){
		return this.gbl;
	}

	public ProgramDiv getProgramDiv(){
		return this.pgrm;
	}

	@Override
	public String toString(int level){
		return (super.getSpaces(level) + token);
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitCode(this, args);
	}
}