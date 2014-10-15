package util.AST;

public class ArithmeticParcel extends AST {

	ArithmeticTerm aTerm = null;
	OpAdd op = null;
	ArithmeticParcel aParcel = null;

	public ArithmeticParcel(ArithmeticTerm aTerm){
		this.aTerm = aTerm;
	}
	public ArithmeticParcel(ArithmeticTerm aTerm, OpAdd op, ArithmeticParcel aParcel){
		this.aTerm = aTerm;
		this.op = op;
		this.aParcel = aParcel;
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