package util.AST;

public  class Identifier extends Terminal {	
	public int kind = Types.VARIAVEL;
	public Object declaration;
	public String type;

	public Identifier(String spelling) { 
		super(spelling); 
	}
	
	public String getType(){
		String type = "";
		if(this.kind == Types.VARIAVEL){
			type = "variavel";
		} else if(this.kind == Types.FUNCAO){
			type = "funcao";
		} else if(this.kind == Types.PARAMETRO){
			type = "parametro";
		}
		return type;
	}


}