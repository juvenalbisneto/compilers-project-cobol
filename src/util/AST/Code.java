package util.AST;

import java.util.*;

public class Code extends AST {

	GlobalDataDiv gbl;
	ProgramDiv pgrm;


	public Code(GlobalDataDiv gbl, ProgramDiv pgrm){
		this.gbl=gbl;
		this.pgrm=pgrm;
	}
	
	public Code(ProgramDiv pgrm){
		this.pgrm=pgrm;
	}

	public GlobalDataDiv getGlobalDataDiv(){
		return this.gbl;
	}

	public ProgramDiv getProgramDiv(){
		return this.pgrm;
	}


}