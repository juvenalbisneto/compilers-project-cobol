package util.AST;

import java.util.ArrayList;

public class FunctionCall extends Command {
	Identifier id;
	ArrayList<Identifier> ids = new ArrayList<Identifier>();
	
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

}
