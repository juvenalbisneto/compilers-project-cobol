package util.AST;

import checker.SemanticException;

public class ArithmeticExpression extends Expression {
	ArithmeticParcel aparcel = null;
	Number number = null;

	public ArithmeticExpression(ArithmeticParcel aparcel){
		this.aparcel = aparcel;
	}
	public ArithmeticExpression(Number number){
		this.number = number;
	}

	public ArithmeticParcel getArithmeticParcel(){
		return this.aparcel;
	}
	public Number getNumber(){
		return this.number;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitArithmeticExpression(this, args);
	}
}