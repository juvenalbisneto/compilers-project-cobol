package util.AST;

import java.util.*;

public class ProgramDiv extends AST {
	ArrayList<Function> func = null;
	MainProc main=null;

	public ProgramDiv (ArrayList<Function> func, MainProc main){
		this.func=func;
		this.main=main;
	}

	public ArrayList<Function> getArrayFunction(){
		return this.func;
	}
	public ArrayList<MainProc> getMainProc(){
		return this.main;
	}
	

}