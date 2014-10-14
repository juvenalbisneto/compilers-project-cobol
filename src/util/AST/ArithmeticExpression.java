package util.AST;

import java.util.*;

public class ArithmeticExpression extends Expression {

	ArithmeticParcel aparcel=null;

	public ArithmeticExpression(ArithmeticParcel aparcel){
		this.aparcel=aparcel;
	}

	public ArithmeticParcel getArithmeticParcel(){
		return this.aparcel;
	}

}