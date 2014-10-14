package util.AST;

import java.util.*;

public class Return extends Command {
	Identifier id = null;
	Expression exp = null;

	public Return(Identifier id){
		this.id = id;
	}
	public Return(Expression exp){
		this.exp = exp;
	}	
	
	public Identifier getIdentifier(){
		return this.id;
	}
	public Expression getExpression(){
		return this.exp;
	}
}
}