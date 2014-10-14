package util.AST;

import java.util.*;

public class GlobalDataDiv extends AST {
	ArrayList<VarDeclaration> varD = null;

	public GlobalDataDiv (ArrayList<VarDeclaration> varD){
		this.varD=varD;
	}

	public ArrayList<VarDeclaration> getVarDeclaration(){
		return this.varD;
	}
	

}