package util.AST;

import java.util.*;

import checker.SemanticException;

public class Function extends AST {	
	String tipoRetorno;
	Identifier id = null;
	ArrayList<Identifier> params = null;
	ArrayList<String> paramTypes = null;
	ArrayList<VarDeclaration> declarations = null;
	ArrayList<Command> cmds = null;
	
	public Function(String tipoRetorno, Identifier id, ArrayList<Identifier> params, ArrayList<String> paramTypes, ArrayList<VarDeclaration> declarations, ArrayList<Command> cmds) {
		super();
		this.tipoRetorno = tipoRetorno;
		this.id = id;
		this.params = params;
		this.paramTypes = paramTypes;
		this.declarations = declarations;
		this.cmds = cmds;
	}
	
	public String getTipoRetorno(){
		return this.tipoRetorno;
	}
	public Identifier getID(){
		return this.id;
	}
	public ArrayList<Identifier> getParams(){
		return this.params;
	}
	public ArrayList<String> getParamsTypes(){
		return this.paramTypes;
	}
	public ArrayList<VarDeclaration> getVarDeclarations(){
		return this.declarations;
	}
	public ArrayList<Command> getCommands(){
		return this.cmds;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitFunction(this, args);
	}
}