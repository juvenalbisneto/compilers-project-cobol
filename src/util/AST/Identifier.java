package util.AST;

import checker.SemanticException;

public  class Identifier extends Terminal {	
	public int kind = Types.VARIAVEL;
	public Object declaration;
	public String type;
	public int memoryPosition;
	public boolean local = true;

	public Identifier(String spelling) { 
		super(spelling); 
	}
	
	public String getKind(){
		String kind = "";
		if(this.kind == Types.VARIAVEL){
			kind = "variavel";
		} else if(this.kind == Types.FUNCAO){
			kind = "funcao";
		} else if(this.kind == Types.PARAMETRO){
			kind = "parametro";
		}
		return kind;
	}

	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitIdentifier(this, args);
	}
}