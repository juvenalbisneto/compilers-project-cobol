package util.AST;

public  class Identifier extends Terminal {	
	public int kind = Types.VARIAVEL;
	public Object declaration;
	public String type;

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


}