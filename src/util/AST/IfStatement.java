package util.AST;

import java.util.*;

import checker.SemanticException;

public class IfStatement extends Command {
	BooleanExpression bexpression = null;
	ArrayList<Command> cmd_if = null;
	ArrayList<Command> cmd_else = null;

	public IfStatement(BooleanExpression bexpression, ArrayList<Command> cmd_if){
		this.cmd_if = cmd_if;
		this.bexpression = bexpression;
	}
	public IfStatement(BooleanExpression bexpression, ArrayList<Command> cmd_if, ArrayList<Command> cmd_else){
		this.cmd_if = cmd_if;
		this.bexpression = bexpression;
		this.cmd_else = cmd_else;
	}
	
	public BooleanExpression getBooleanExpression(){
		return this.bexpression;
	}
	public ArrayList<Command> getCommandIF(){
		return this.cmd_if;
	}
	public ArrayList<Command> getCommandElse(){
		return this.cmd_else;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitIfStatement(this, args);
	}
}