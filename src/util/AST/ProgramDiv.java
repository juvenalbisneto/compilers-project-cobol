package util.AST;

import java.util.*;

import checker.SemanticException;

public class ProgramDiv extends AST {
	ArrayList<Function> func = null;
	MainProc main = null;

	public ProgramDiv (ArrayList<Function> func, MainProc main){
		this.func = func;
		this.main = main;
	}

	public ArrayList<Function> getArrayFunction(){
		return this.func;
	}
	public MainProc getMainProc(){
		return this.main;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitProgramDiv(this, args);
	}
}