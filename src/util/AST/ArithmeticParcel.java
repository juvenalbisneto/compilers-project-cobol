package util.AST;

public class ArithmeticParcel extends ArithmeticFactor {

	ArithmeticTerm aterm = null;
	OpAdd op = null;
	ArithmeticParcel aparcel = null;

	public ArithmeticParcel(ArithmeticTerm aterm){
		this.aterm = aterm;
	}
	public ArithmeticParcel(ArithmeticTerm aterm, OpAdd op, ArithmeticParcel aparcel){
		this.aterm = aterm;
		this.op = op;
		this.aparcel = aparcel;
	}

	public ArithmeticTerm getArithmeticTerm(){
		return this.aterm;
	}
	public OpAdd getOpAdd(){
		return this.op;
	}
	public ArithmeticParcel getArithmeticParcel(){
		return this.aparcel;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}