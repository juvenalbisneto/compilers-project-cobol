package util.AST;

import java.util.*;

public class Until extends Command {
	BooleanExpression bexpression = null;
	ArrayList<Command> cmd = null;
	
	public Until(BooleanExpression bexpression, ArrayList<Command> cmd){
		this.bexpression = bexpression;
		this.cmd = cmd;
	}
	public BooleanExpression getBooleanExpression(){
		return this.bexpression;
	}
	public ArrayList<Command> getCommand(){
		return this.cmd;
	}

}