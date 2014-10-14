package util.AST;

import java.util.*;

public class MainProc extends AST {
	ArrayList<VarDeclaration> varD=null;
	ArrayList<Command> cmd=null;


	public MainProc(ArrayList<VarDeclaration> varD, ArrayList<Command> cmd){
		this.varD=varD;
		this.cmd=cmd;
	}

	public getArrayVarDeclaration(){
		return this.varD;
	}

	public getArrayCommand(){
		return this.cmd;
	}

}