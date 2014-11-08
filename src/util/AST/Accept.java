package util.AST;

import checker.SemanticException;

public class Accept extends Command {
		Identifier id = null;
		Expression exp = null;
		FunctionCall func = null;

		public Accept(Identifier id, Expression exp){
			this.id = id;
			this.exp = exp;
		}
		public Accept(Identifier id, FunctionCall func){
			this.id = id;
			this.func = func;
		}

		public Identifier getIdentifier(){
			return this.id;
		}
		public Expression getExpression(){
			return this.exp;
		}
		public FunctionCall getFunctionCall(){
			return this.func;
		}
		
		@Override
		public String toString(int level) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object visit(Visitor v, Object args) throws SemanticException{
			return v.visitAccept(this, args);
		}
}