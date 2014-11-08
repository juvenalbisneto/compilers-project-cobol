package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class GlobalDataDiv extends AST {
	ArrayList<VarDeclaration> varD = null;

	public GlobalDataDiv (ArrayList<VarDeclaration> varD){
		this.varD = varD;
	}

	public ArrayList<VarDeclaration> getVarDeclaration(){
		return this.varD;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitGlobalDataDiv(this, args);
	}
}