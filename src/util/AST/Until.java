package util.AST;

import java.util.ArrayList;

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
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}