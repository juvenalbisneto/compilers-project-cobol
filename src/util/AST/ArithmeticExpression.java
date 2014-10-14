package util.AST;

import java.util.*;

public class ArithmeticExpression extends Expression {
	ArithmeticParcel aparcel = null;

	public ArithmeticExpression(ArithmeticParcel aparcel){
		this.aparcel = aparcel;
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