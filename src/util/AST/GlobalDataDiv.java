package util.AST;

import java.util.ArrayList;

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
}