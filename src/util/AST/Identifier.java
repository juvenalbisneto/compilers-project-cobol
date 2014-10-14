package util.AST;

public  class Identifier extends Terminal {	
	public int type = Types.VARIAVEL;
	public int local = Types.LOCAL;

	public Identifier(String spelling) { 
		super(spelling); 
	}
	
	public String getType(){
		String type = "";
		if(this.type == Types.VARIAVEL){
			type = "variavel";
		} else if(this.type == Types.FUNCAO){
			type = "funcao";
		} else if(this.type == Types.PARAMETRO){
			type = "parametro";
		}
		return type;
	}


}