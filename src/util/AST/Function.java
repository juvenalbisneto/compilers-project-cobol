package util.AST;

import java.util.*;

public class Function extends AST {	
	String tipoRetorno;
	Identifier id;
	ArrayList<Identifier> params;
	ArrayList<VarDeclaration> declarations;
	ArrayList<Command> cmds;
	
	public Function(String tipoRetorno, Identifier id, ArrayList<Identifier> params, ArrayList<VarDeclaration> declarations, ArrayList<Command> cmds) {
		super();
		this.tipoRetorno = tipoRetorno;
		this.id = id;
		this.params = params;
		this.declarations = declarations;
		this.cmds = cmds;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}