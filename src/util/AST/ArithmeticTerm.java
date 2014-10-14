package util.AST;

public class ArithmeticTerm extends Expression {
	ArithmeticFactor afactor = null;
	OpMult op = null;
	ArithmeticTerm aterm = null;

	public ArithmeticTerm(ArithmeticFactor afactor){
		this.afactor = afactor;
	}

	public ArithmeticTerm(ArithmeticFactor afactor, OpMult op, ArithmeticTerm aterm){
		this.aterm = aterm;
		this.op = op;
		this.afactor = afactor;
	}

	public ArithmeticFactor getArithmeticFactor(){
		return this.afactor;
	}
	public OpMult getOpMult(){
		return this.op;
	}
	public ArithmeticTerm getArithmeticTerm(){
		return this.aterm;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}
