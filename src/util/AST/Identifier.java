package util.AST;

import java.util.*;

public  class Identifier extends Terminal {
//peguei do de sabs p n ficar vazio, mas n sei bem como funfa e ja esta tarde p descobrir
	public int type = 0;

	public Identifier(String spelling) { 
		super(spelling); 
	}
	
	public String getType(){
		String type = "";
		if(this.type == 0){
			type = "variavel";
		}else if(this.type == 1){
			type = "funcao";
		}else if(this.type == 2){
			type =  "parametro";
		}
		return type;
	}


}