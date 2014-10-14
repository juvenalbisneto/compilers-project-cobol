package util.AST;

public class Assignment extends Command {
		Identifier id = null;
		Expression exp = null;
		FunctionCall func = null;

		public Assignment(Identifier id, Expression exp){
			this.id = id;
			this.exp = exp;
		}
		public Assignment(Identifier id, FunctionCall func){
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
}