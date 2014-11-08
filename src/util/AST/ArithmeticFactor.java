package util.AST;

import checker.SemanticException;

public class ArithmeticFactor extends AST {
	Identifier id = null;
	Number num = null;
	ArithmeticParcel aParcel = null;

	public ArithmeticFactor(Identifier id){
		this.id = id;
	}
	public ArithmeticFactor(Number num){
		this.num = num;
	}
	public ArithmeticFactor(ArithmeticParcel aParcel){
		this.aParcel = aParcel;
	}

	public Identifier getId(){
		return this.id;
	}
	public Number getNumber(){
		return this.num;
	}
	public ArithmeticParcel getArithmeticParcel(){
		return this.aParcel;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitArithmeticFactor(this, args);
	}
}