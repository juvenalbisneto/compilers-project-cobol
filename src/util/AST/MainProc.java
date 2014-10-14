package util.AST;

import java.util.*;

public class MainProc extends AST {
	ArrayList<VarDeclaration> varD = new ArrayList<VarDeclaration>();
	ArrayList<Command> cmds = new ArrayList<Command>();


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

}