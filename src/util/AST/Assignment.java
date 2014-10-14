package util.AST;

import java.util.*;

public class Assignment extends Command {
		Identifier id = null;
		Expression exp = null;
		FunctionCall func = null;

		public Assignment(Identifier id, FunctionCall func){
			this.id = id;
			this.func = func;
		}

		public Assignment(Identifier id, Expression exp){
			this.id = id;
			this.exp = exp;
		}

		public Identifier getIdentifier(){
			return this.id;
		}

		public Expression getExpression(){
			return this.exp;
		}
		public Identifier getFunctionCall(){
			return this.func;
		}

}