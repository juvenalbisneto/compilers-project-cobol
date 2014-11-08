package util.AST;

import java.util.*;

import checker.SemanticException;

public class MainProc extends AST {
	ArrayList<VarDeclaration> varD = null;
	ArrayList<Command> cmds = null;


	public MainProc(ArrayList<VarDeclaration> varD, ArrayList<Command> cmds){
		this.varD = varD;
		this.cmds = cmds;
	}

	public ArrayList<VarDeclaration> getArrayVarDeclarations(){
		return this.varD;
	}

	public ArrayList<Command> getArrayCommands(){
		return this.cmds;
	}

	@Override
	public String toString(int level) {
		return null;
	}

	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitMainProc(this, args);
	}
}