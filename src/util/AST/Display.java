package util.AST;

import java.util.*;

public class Display extends Command {
	Identifier id = null;
	Expression exp = null;

	public Display(Identifier id){
		this.id = id;
	}
	public Display(Expression exp){
		this.exp = exp;
	}
}