package util.AST;

public class ArithmeticParcel extends AST {

	ArithmeticTerm aTerm = null;
	OpAdd op = null;
	ArithmeticParcel aParcel = null;

	public ArithmeticParcel(ArithmeticTerm aterm){
		this.aTerm = aterm;
	}
	public ArithmeticParcel(ArithmeticTerm aterm, OpAdd op, ArithmeticParcel aparcel){
		this.aTerm = aterm;
		this.op = op;
		this.aParcel = aparcel;
	}

	public ArithmeticTerm getArithmeticTerm(){
		return this.aTerm;
	}
	public OpAdd getOpAdd(){
		return this.op;
	}
	public ArithmeticParcel getArithmeticParcel(){
		return this.aParcel;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}