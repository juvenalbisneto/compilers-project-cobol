package util.AST;

public  class Identifier extends Terminal {
	public static final int VARIAVEL = 0, FUNCAO = 1, PARAMETRO = 2;
	public static final int GLOBAL = 0, LOCAL = 1;
	
	public int type = VARIAVEL;
	public int local = LOCAL;

	public Identifier(String spelling) { 
		super(spelling); 
	}
	
	public String getType(){
		String type = "";
		if(this.type == VARIAVEL){
			type = "variavel";
		} else if(this.type == FUNCAO){
			type = "funcao";
		} else if(this.type == PARAMETRO){
			type = "parametro";
		}
		return type;
	}


}