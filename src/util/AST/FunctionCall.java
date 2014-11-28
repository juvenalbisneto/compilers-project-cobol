package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class FunctionCall extends Command {
	public Identifier id = null;
	ArrayList<Identifier> ids = new ArrayList<Identifier>();
	
	public FunctionCall (Identifier nome){
		this.id = nome;
	}
	public FunctionCall (Identifier nome, ArrayList<Identifier> ids){
		this.id = nome;
		this.ids = ids;
	}
	
	public Identifier getId(){
		return this.id;
	}
	
	public ArrayList<Identifier> getParams(){
		return this.ids;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitFunctionCall(this, args);
	}
}
